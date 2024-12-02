package org.acme.features.fruit.application.dto;

import java.util.ArrayList;
import java.util.List;

import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.model.Fruit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FruitListResultDto extends Interaction {

  public static FruitListResultDto from(Interaction prev, List<Fruit> fruits) {
    return FruitListResultDto.builder()
        .fruits(new ArrayList<>(fruits.stream().map(FruitDto::from).toList())).build(prev);
  }

  public static FruitListResultDto fromDto(Interaction prev, List<FruitDto> fruits) {
    return FruitListResultDto.builder().fruits(new ArrayList<>(fruits)).build(prev);
  }

  private final List<FruitDto> fruits;

  public boolean isEmpty() {
    return fruits.isEmpty();
  }

  public int size() {
    return fruits.size();
  }

  public void append(FruitListResultDto next, int limit) {
    this.fruits.addAll(next.size() > limit ? next.getFruits().subList(0, limit) : next.getFruits());
  }

}
