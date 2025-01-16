package org.acme.features.market.place.application.usecase.delete;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.application.PlaceDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlaceDeleteResult {

  /**
   * The source interaction
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Interaction command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Optional<PlaceDto> place;
}