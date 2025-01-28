package org.acme.common.security.scope;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ScopeAllow {
  @NonNull
  private final String resource;

  @NonNull
  private final String name;

  public boolean match(String resource, String action) {
    return this.resource.equals(resource) && this.name.equals(action);
  }
}
