package org.acme.features.fruit.domain.query;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RegisterForReflection
public class FruitCursor {
  private final String sinceUid;
  private final String sinceName;
  private final FruitOrder[] order;
}
