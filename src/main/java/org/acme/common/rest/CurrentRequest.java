/* @autogenerated */
package org.acme.common.rest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.acme.common.action.Interaction;
import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
@RequiredArgsConstructor
public class CurrentRequest {
  private final SecurityIdentity security;
  private final JsonWebToken jwt;
  private final UriInfo request;

  public String getPublicHost() {
    return "http://localhost:8090";
  }

  public boolean isAnonymous() {
    return security.isAnonymous();
  }

  private Actor getActor() {
    Actor.ActorBuilder builder = Actor.builder();
    if (security.isAnonymous()) {
      System.err.println("Usuario anonimo");
      builder = builder.autenticated(false).roles(List.of());
    } else {
      System.err.println("Principal: " + security.getPrincipal().getName());
      System.err.println("Roles: " + security.getRoles());
      builder = builder.name(security.getPrincipal().getName()).roles(List.of())
          // .roles( new ArrayList<>( security.getRoles() ))
          .tenant("18e4ee65-02b4-4181-b269-b848a8a35164");
    }
    return builder.build();
  }

  private Connection getConnection() {
    return Connection.builder().request(request.getPath()).build();
  }

  // FIXME: crear un método "resolve" que reciba como entrada una lambda de Interacion => response
  // lo que permite recuperar actor y conexión de forma asincrona y esperar para invocar cuando
  // este resueltos
  public <T> Response resolve(Function<Interaction, CompletionStage<T>> callback) {
    return resolve(callback, null);
  }

  public <T> Response resolve(Function<Interaction, CompletionStage<T>> callback,
      Function<T, Response> customize) {
    Actor actor = getActor();
    Connection conn = getConnection();
    Interaction inter = new Interaction(Interaction.builder().actor(actor).connection(conn)) {};
    return response(callback.apply(inter), customize);
  }

  private <T> Response response(CompletionStage<T> future, Function<T, Response> customize) {
    try {
      T response = future.toCompletableFuture().get(1, TimeUnit.SECONDS);
      @SuppressWarnings("unchecked")
      Optional<T> opresponse =
          (response instanceof Optional<?> opt) ? (Optional<T>) opt : Optional.of(response);
      return opresponse
          .map(res -> null == customize ? Response.ok(response).build() : customize.apply(response))
          .orElseGet(() -> Response.status(404).build());
    } catch (ExecutionException e) {
      Throwable th = e.getCause();
      th.printStackTrace();
      if (th instanceof RuntimeException re) {
        throw re;
      } else {
        return Response.serverError().build();
      }
    } catch (InterruptedException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  // public ActorRequest getCurrentActorRequest() {
  // String base = request.getBaseUri().toString();
  // while (base.endsWith("/")) {
  // base = base.substring(0, base.length() - 1);
  // }
  // boolean anonimous = security.isAnonymous();
  // Map<String, String> claims = new HashMap<>();
  // if (!anonimous) {
  // List<String> fixed = Arrays.asList("iss", "scope", "org", "azp", "tenant", "raw_token", "typ",
  // "jti", "grant_type");
  //
  // for (String string : jwt.getClaimNames()) {
  // if (!fixed.contains(string)) {
  // Object value = jwt.getClaim(string);
  // if (value instanceof String) {
  // claims.put(string, (String) value);
  // }
  // }
  // }
  // }
  // Locale language = headers.getLanguage();
  //
  // return ActorRequest.builder()
  // .identity(ActorRequest.Identity.builder().anonymous(anonimous)
  // .token(headers.getHeaderString("Authorization"))
  // .name(anonimous ? null : security.getPrincipal().getName()).issuer(claimAsString("iss"))
  // .roles(anonimous ? Arrays.asList() : security.getRoles().stream().toList())
  // .claims(Collections.unmodifiableMap(claims)).scopes(loadScopes(jwt.getClaim("scope")))
  // .build())
  // .connection(ActorRequest.Connection.builder().baseRequest(base)
  // .application(claimAsString("azp")).remote(true).startTime(ZonedDateTime.now())
  // .locale(null == language ? Locale.getDefault() : language).build())
  // .build();
  // }
  //
  // @SuppressWarnings({"unchecked"})
  // private List<String> loadScopes(Object scope) {
  // List<String> scopes;
  // if (null == scope) {
  // scopes = Arrays.asList();
  // } else if (scope instanceof @SuppressWarnings("rawtypes") List li) {
  // scopes = li.stream().map(Object::toString).toList();
  // } else {
  // scopes = Arrays.asList(scope.toString());
  // }
  // return scopes;
  // }
  //
  // private String claimAsString(String name) {
  // Object value = jwt.getClaim(name);
  // return value != null ? value.toString() : null;
  // }

}
