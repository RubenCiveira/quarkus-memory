package org.acme.common.security;

import java.util.List;
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

  public Optional<String> getTenant() {
    return Optional.ofNullable(tenant);
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public boolean isAutenticated() {
    return autenticated;
  }

  public boolean hasAnyRole(String... strings) {
    return true;
  }
}
