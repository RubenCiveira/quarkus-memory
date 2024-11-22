package org.acme.features.fruit.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper =  true)
public class Fruit extends FruitRef {
  private String name;
}
