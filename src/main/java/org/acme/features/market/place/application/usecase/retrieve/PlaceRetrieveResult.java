package org.acme.features.market.place.application.usecase.retrieve;

import java.time.OffsetDateTime;
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
public class PlaceRetrieveResult {

  /**
   * The source interaction
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Optional<PlaceDto> place;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final OffsetDateTime since;
}
