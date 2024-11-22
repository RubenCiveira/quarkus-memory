package org.acme.features.fruit.domain.interaction.command;
import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.model.Fruit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
public abstract class FruitWrite extends Interaction {
  private final Fruit fruit;
}
