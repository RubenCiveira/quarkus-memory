package org.acme.common.security;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RegisterForReflection
public class Allow {
  private boolean allowed;
  // private String name;
  private String description;

  // private final Actor actor;
  // private final Connection connection;
}
