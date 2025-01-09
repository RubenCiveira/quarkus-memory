package org.acme.common.security;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Actor {
  private final String name;
  private final String tenant;
  private final boolean autenticated;
  private final List<String> roles;
  private final Map<String, String> claims;

  public Optional<String> getTenant() {
    return Optional.ofNullable(tenant);
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public boolean isAutenticated() {
    return autenticated;
  }

  public String getClaim(String name) {
    return claims.get(name);
  }

  public boolean hasRole(String role) {
    return roles.contains(role);
  }

  public boolean hasAnyRole(String... strings) {
    for (String string : strings) {
      if (hasRole(string)) {
        return true;
      }
    }
    return false;
  }
}
