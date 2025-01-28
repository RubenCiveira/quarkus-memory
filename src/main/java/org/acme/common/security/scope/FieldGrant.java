package org.acme.common.security.scope;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class FieldGrant {
  @NonNull
  private String name;

  @NonNull
  private String view;

  @NonNull
  private final String resource;

  public boolean match(String resource, String view) {
    return this.resource.equals(resource) && this.view.equals(view);
  }
}
