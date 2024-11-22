package org.acme.features.fruit.domain.query;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RegisterForReflection
public class FruitFilter {
  private String uid;
  private String[] uids;
  private String like;
}
