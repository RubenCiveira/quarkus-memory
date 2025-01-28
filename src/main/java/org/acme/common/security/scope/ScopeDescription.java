package org.acme.common.security.scope;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ScopeDescription {
  @NonNull
  private final String name;

  private final String description;

  private final List<String> required;
}
