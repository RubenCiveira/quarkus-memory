package org.acme.features.fruit.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitRef {
  @EqualsAndHashCode.Include
  private String uid;
}
