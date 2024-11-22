package org.acme.features.fruit.domain.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class FruitRef {
  private String uid;
}
