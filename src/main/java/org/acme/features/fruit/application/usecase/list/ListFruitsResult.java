package org.acme.features.fruit.application.usecase.list;

import java.util.List;
import org.acme.features.fruit.domain.model.Fruit;
import org.acme.features.fruit.domain.model.FruitDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListFruitsResult {
  public static ListFruitsResult from(List<Fruit> fruits) {
    return ListFruitsResult.builder().fruits(fruits.stream().map(FruitDto::from).toList()).build();
  }

  private List<FruitDto> fruits;
}
