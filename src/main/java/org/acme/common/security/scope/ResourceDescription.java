package org.acme.common.security.scope;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ResourceDescription {
  @NonNull
  private final String name;
  private final String description;
}
