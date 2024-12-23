package org.acme.features.market.place.application.interaction.result;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.place.application.interaction.PlaceDto;
import org.acme.features.market.place.application.interaction.command.PlaceUpdateCommand;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlaceUpdateResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final PlaceUpdateCommand command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletionStage<Optional<PlaceDto>> place;
}
