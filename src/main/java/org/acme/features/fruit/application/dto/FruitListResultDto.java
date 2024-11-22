package org.acme.features.fruit.application.dto;

import java.util.ArrayList;
import java.util.List;
import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.interaction.result.FruitListResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FruitListResultDto extends Interaction {

  public static FruitListResultDto from(FruitListResult prev) {
    return FruitListResultDto.builder().actor(prev.getActor()).connection(prev.getConnection())
        .fruits(new ArrayList<>(prev.getFruits().stream().map(FruitDto::from).toList())).build();
  }

  private final List<FruitDto> fruits;
  
  public boolean isEmpty() {
    return fruits.isEmpty();
  }
  
  public int size() {
    return fruits.size();
  }

  public void append(FruitListResultDto next, int limit) {
    this.fruits.addAll( next.size() > limit ? next.getFruits().subList(0, limit) : next.getFruits() );
  }
  
}
