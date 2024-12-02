package org.acme.features.fruit.domain.interaction.command;

import org.acme.common.action.Interaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitDelete extends Interaction {
  private final String uid;
}
