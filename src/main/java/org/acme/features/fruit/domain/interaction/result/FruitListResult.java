package org.acme.features.fruit.domain.interaction.result;

import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitListResult extends Interaction {
  public static FruitListResult from(Interaction prev, FruitSlice fruits) {
    return FruitListResult.builder().actor(prev.getActor()).connection(prev.getConnection())
        .fruits(fruits).build();
  }

  private final FruitSlice fruits;
  
  public FruitSlice next() {
    return fruits.next();
  }
}
