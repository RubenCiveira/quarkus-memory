package org.acme.common.security.rcab;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.acme.common.security.Actor;
import org.acme.common.security.RbacStore;
import org.acme.common.security.scope.FieldDescription;
import org.acme.common.security.scope.FieldGrant;
import org.acme.common.security.scope.FieldGrantList;
import org.acme.common.security.scope.ResourceDescription;
import org.acme.common.security.scope.ScopeAllow;
import org.acme.common.security.scope.ScopeAllowList;
import org.acme.common.security.scope.ScopeDescription;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@IfBuildProperty(name = "mp.rcab.model", stringValue = "phylax")
@RequiredArgsConstructor
public class Phylax implements RbacStore {
  private final String KIND = "phylax";
  private final ObjectMapper mapper;
  private final @ConfigProperty(name = "mp.rcab.model") String model;
  private final @ConfigProperty(name = "mp.rcab.phylax.api-key") String apiKey;
  private final @ConfigProperty(name = "mp.rcab.phylax.audience") String audience;
  private final @ConfigProperty(name = "mp.rcab.phylax.register.scope") String registerScopeUrl;
  private final @ConfigProperty(
      name = "mp.rcab.phylax.register.schema") String registerPropertiesUrl;
  private final @ConfigProperty(name = "mp.rcab.phylax.check.grant") String checkGrantsUrl;

  private final HttpClient client = HttpClient.newHttpClient();
  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  private transient CompletionStage<List<PhylaxGrants>> readScopes = null;
  private transient OffsetDateTime scopesExpiration = OffsetDateTime.now();
  private transient Map<String, Map<String, Object>> scopesToRegister = new HashMap<>();
  private transient Map<String, Map<String, Object>> propertiesToRegister = new HashMap<>();

  @Override
  public boolean isActive() {
    return KIND.equals(model);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void registerResourceAction(ResourceDescription resource, ScopeDescription action) {
    if (!scopesToRegister.containsKey(resource.getName())) {
      scopesToRegister.put(resource.getName(),
          Map.of("resource",
              Map.of("name", resource.getName(), "description", resource.getDescription()),
              "scopes", new ArrayList<>()));
    }
    ((List<Object>) (scopesToRegister.get(resource.getName()).get("scopes")))
        .add(Map.of("name", action.getName(), "description", action.getDescription()));
  }

  @Override
  @SuppressWarnings("unchecked")
  public void registerResourceField(ResourceDescription resource, FieldDescription field) {
    if (!propertiesToRegister.containsKey(resource.getName())) {
      propertiesToRegister.put(resource.getName(),
          Map.of("resource",
              Map.of("name", resource.getName(), "description", resource.getDescription()),
              "properties", new ArrayList<>()));
    }
    ((List<Object>) (propertiesToRegister.get(resource.getName()).get("properties")))
        .add(Map.of("name", field.getName(), "description", field.getDescription()));
  }

  @Override
  public CompletionStage<ScopeAllowList> checkRoleScopes(Actor actor) {
    return cachedCallCheckScopes().thenApply(grants -> {
      List<String> roles = actor.getRoles().stream().filter(role -> role.startsWith(audience + "."))
          .map(role -> role.substring(audience.length() + 1)).toList();
      ScopeAllowList descriptions = new ScopeAllowList();
      grants.stream().filter(grant -> roles.contains(grant.getRolename())).forEach(grant -> {
        grant.getAllowedScopes().forEach(scope -> {
          String[] parts = scope.split("\\:");
          descriptions.add(ScopeAllow.builder().name(parts[1]).resource(parts[0]).build());
        });
      });
      return descriptions;
    });
  }

  @Override
  public CompletionStage<FieldGrantList> checkRoleProperties(Actor actor) {
    return cachedCallCheckScopes().thenApply(grants -> {
      List<String> roles = actor.getRoles().stream().filter(role -> role.startsWith(audience + "."))
          .map(role -> role.substring(audience.length() + 1)).toList();

      FieldGrantList descriptions = new FieldGrantList();
      grants.stream().filter(grant -> roles.contains(grant.getRolename())).forEach(grant -> {
        grant.getRestrictedFields().entrySet().forEach(entry -> {
          entry.getValue().forEach(field -> {
            String[] parts = field.split("\\:");
            descriptions.add(FieldGrant.builder().view(entry.getKey()).name(parts[1])
                .resource(parts[0]).build());
          });
        });
      });
      return descriptions;
    });
  }

  void complete(@Observes @Priority(100) StartupEvent ev) {
    try {
      callRegisterScopes();
    } catch (IOException | InterruptedException | URISyntaxException e) {
      executorService.schedule(() -> {
        try {
          callRegisterScopes();
        } catch (Exception er) {
          e.printStackTrace();
        }
      }, 10, TimeUnit.SECONDS);
    }
    try {
      callRegisterProperties();
    } catch (Exception e) {
      executorService.schedule(() -> {
        try {
          callRegisterProperties();
        } catch (IOException | InterruptedException | URISyntaxException er) {
          e.printStackTrace();
        }
      }, 10, TimeUnit.SECONDS);
    }
  }

  private void callRegisterScopes() throws IOException, InterruptedException, URISyntaxException {
    String json = mapper.writeValueAsString(scopesToRegister.values());

    HttpRequest request = HttpRequest.newBuilder().uri(new URI(registerScopeUrl))
        .header("api-key", apiKey).header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() >= 400) {
      throw new IOException("Error " + response.statusCode() + ": " + response.body());
    }
    scopesToRegister.clear();
  }

  private void callRegisterProperties()
      throws IOException, InterruptedException, URISyntaxException {
    String json = mapper.writeValueAsString(propertiesToRegister.values());

    HttpRequest request = HttpRequest.newBuilder().uri(new URI(registerPropertiesUrl))
        .header("api-key", apiKey).header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() >= 400) {
      throw new IOException("Error " + response.statusCode() + ": " + response.body());
    }
    propertiesToRegister.clear();
  }

  private synchronized CompletionStage<List<PhylaxGrants>> cachedCallCheckScopes() {
    if (scopesExpiration.isBefore(OffsetDateTime.now())) {
      scopesExpiration = OffsetDateTime.now().plus(15, ChronoUnit.MINUTES);
      readScopes = callCheckScopes();
    }
    return readScopes;
  }

  private CompletionStage<List<PhylaxGrants>> callCheckScopes() {
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(checkGrantsUrl))
          .header("api-key", apiKey).header("Content-Type", "application/json").GET().build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() >= 400) {
        throw new IllegalArgumentException(
            "Error " + response.statusCode() + ": " + response.body());
      } else {
        try {
          List<PhylaxGrants> readValue =
              mapper.readValue(response.body(), new TypeReference<List<PhylaxGrants>>() {});
          return CompletableFuture.completedStage(readValue);
        } catch (JsonProcessingException e) {
          throw new IllegalArgumentException(
              "Unable to parse as a phylax grants response" + response.body(), e);
        }
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new IllegalArgumentException("Unable to determinate phylax url", e);
    }
  }

}
