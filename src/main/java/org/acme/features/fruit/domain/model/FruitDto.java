package org.acme.features.fruit.domain.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RegisterForReflection
public class FruitDto {
  public static FruitDto from(Fruit fruit) {
    return FruitDto.builder().uid(fruit.getUid()).name(fruit.getName()).build();
  }

  private String uid;
  private String name;
}
