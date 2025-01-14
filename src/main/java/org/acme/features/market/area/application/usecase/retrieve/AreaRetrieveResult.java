package org.acme.features.market.area.application.usecase.retrieve;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.area.application.AreaDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AreaRetrieveResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletionStage<Optional<AreaDto>> area;

  /**
   * The source interaction
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Interaction interaction;
}
