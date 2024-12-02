package org.acme.features.fruit.domain.interaction.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitUpdate extends FruitWrite {
  private String uid;
}
