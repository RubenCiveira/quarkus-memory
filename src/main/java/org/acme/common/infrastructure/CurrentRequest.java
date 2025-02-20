/* @autogenerated */
package org.acme.common.infrastructure;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Objects;
import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.metadata.Timestamped;
import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
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
  private final HttpHeaders headers;
  private final @ConfigProperty(name = "mp.jwt.audiences") String audiences;

  public String getPublicHost() {
    return "http://localhost:8090";
  }

  public boolean isAnonymous() {
    return security.isAnonymous();
  }

  private Optional<String> getFirstHeader(String name) {
    return Optional.ofNullable(headers.getHeaderString(name));
  }

  public Response cacheableResponse(Object stampData, String eTag) {
    return cacheableResponse(stampData, stampData, eTag);
  }

  public Response cacheableResponse(Object stamp, Object data, String eTag) {
    return (((stamp instanceof Timestamped)
        ? ((Timestamped) stamp).getGeneratedAt().map(instant -> {
          boolean needUpdate = getFirstHeader("if-modified-since")
              .map(since -> instant.getEpochSecond() > ZonedDateTime
                  .parse(since, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().getEpochSecond())
              .orElse(true);
          return (needUpdate
              ? Response.ok(data).header("Etag", eTag).header("Last-Modified",
                  instant.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME))
              : Response.notModified(String.valueOf(instant.hashCode())));
        })
        : Optional.<ResponseBuilder>empty()).orElseGet(() -> Response.ok(data))).build();
  }

  public Interaction interaction() {
    Actor actor = getActor();
    Connection conn = getConnection();
    return new Interaction(Interaction.builder().actor(actor).connection(conn)) {};
  }

  public Actor getActor() {
    Actor.ActorBuilder builder = Actor.builder();
    if (security.isAnonymous()) {
      builder = builder.autenticated(false).roles(List.of());
    } else {
      // System.err.println("Principal: " + security.getPrincipal().getName());
      // System.err.println("Roles: " + security.getRoles());
      // System.err.println("Filtrados desde " + audiences);
      // System.err
      // .println("Quedan como " +
      // security.getRoles().stream().map(this::removePrefix).filter(Objects::nonNull).toList() );

      builder = builder.name(security.getPrincipal().getName())
          .roles(security.getRoles().stream().map(this::removePrefix).filter(Objects::nonNull)
              .toList())
          // .roles( new ArrayList<>( security.getRoles() ))
          .tenant("18e4ee65-02b4-4181-b269-b848a8a35164");
    }
    return builder.build();
  }

  public Connection getConnection() {
    String device = headers.getHeaderString("X-Device-ID");
    return Connection.builder()
        .remoteDevice(device)
        .locale( getRequestHeaderLocale() )
        .request(request.getPath()).build();
  }

  private String removePrefix(String role) {
    if (!role.contains(".")) {
      return role;
    }
    return Arrays.asList(audiences.split("\\,")).stream()
        .filter(pref -> role.startsWith(pref + ".")).findFirst().map(pref -> role).orElse(null);
  }
  
  private Locale getRequestHeaderLocale() {
    Locale locale = Locale.getDefault();
    String localeHeader = headers.getHeaderString("Accept-Language");
    if( null != localeHeader ) {
      List<LanguageRange> parse = Locale.LanguageRange.parse(localeHeader);
      if( !parse.isEmpty() ) {
        Locale lookup = Locale.lookup(parse, Arrays.asList( Locale.getAvailableLocales()) );
        locale = null == lookup ? locale : lookup;
      }
    }
    return locale;
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
