package org.acme.features.fruit.application.dto;

import org.acme.features.fruit.domain.model.Fruit;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@RegisterForReflection
public class FruitDto {
  public static FruitDto from(Fruit fruit) {
    return FruitDto.builder().uid(fruit.getUid()).name(fruit.getName()).build();
  }

  @EqualsAndHashCode.Include
  private String uid;
  private String name;
}
