package org.acme.features.market.color.application.usecase.create;

import org.acme.common.action.Interaction;
import org.acme.features.market.color.application.ColorDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ColorCreateCommand extends Interaction {

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final ColorDto dto;
}
