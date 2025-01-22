package org.acme.features.market.fruit.application;

import org.acme.features.market.fruit.domain.model.Fruit;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitPopulateResult {

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final FruitDto dto;

  /**
   * @autogenerated EntityGenerator
   */
  private final Fruit original;
}
