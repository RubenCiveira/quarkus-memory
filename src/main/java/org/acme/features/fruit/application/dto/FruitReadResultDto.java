package org.acme.features.fruit.application.dto;

import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.interaction.result.FruitReadResult;
import org.acme.features.fruit.domain.model.Fruit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitReadResultDto extends Interaction {
  public static FruitReadResultDto from(FruitReadResult prev, Fruit fruit) {
    return FruitReadResultDto.builder().actor(prev.getActor()).connection(prev.getConnection())
        .fruit(FruitDto.from(prev.getFruit())).build();
  }

  public static FruitReadResultDto from(Interaction prev, FruitDto fruit) {
    return FruitReadResultDto.builder().actor(prev.getActor()).connection(prev.getConnection())
        .fruit(fruit).build();
  }

  private final FruitDto fruit;
}
