package org.acme.features.market.fruit.application.usecase.list;

import java.time.OffsetDateTime;
import java.util.List;

import org.acme.features.market.fruit.application.FruitDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitListResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final List<FruitDto> fruits;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final FruitListQuery query;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final OffsetDateTime since;
}
