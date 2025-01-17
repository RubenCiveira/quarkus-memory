package org.acme.features.market.medal.application.usecase.update;

import java.util.Optional;

import org.acme.features.market.medal.application.MedalDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedalUpdateResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final MedalUpdateCommand command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Optional<MedalDto> medal;
}
