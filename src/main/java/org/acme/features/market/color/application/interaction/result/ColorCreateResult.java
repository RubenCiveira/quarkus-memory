package org.acme.features.market.color.application.interaction.result;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.color.application.interaction.ColorDto;
import org.acme.features.market.color.application.interaction.command.ColorCreateCommand;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ColorCreateResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletionStage<Optional<ColorDto>> color;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final ColorCreateCommand command;
}
