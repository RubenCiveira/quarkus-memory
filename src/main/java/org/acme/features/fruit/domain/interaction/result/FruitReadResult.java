package org.acme.features.fruit.domain.interaction.result;

import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.model.Fruit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitReadResult extends Interaction {
  public static FruitReadResult from(Interaction prev, Fruit fruit) {
    return FruitReadResult.builder().actor(prev.getActor()).connection(prev.getConnection())
        .fruit(fruit).build();
  }

  private final Fruit fruit;
}
