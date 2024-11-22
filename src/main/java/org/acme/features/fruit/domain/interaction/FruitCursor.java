package org.acme.features.fruit.domain.interaction;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;

@Data
@RegisterForReflection
@Builder(toBuilder = true)
public class FruitCursor {
  private final Integer limit;
  private final String sinceUid;
  private final String sinceName;
  private final FruitOrder[] order;
}
