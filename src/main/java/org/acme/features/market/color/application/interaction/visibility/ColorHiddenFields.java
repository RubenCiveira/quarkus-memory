package org.acme.features.market.color.application.interaction.visibility;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.color.domain.model.ColorRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
public class ColorHiddenFields extends Interaction {

  /**
   * @autogenerated HiddenFieldsGenerator
   */
  private final ColorRef color;

  /**
   * The list of hidden fields
   *
   * @autogenerated HiddenFieldsGenerator
   */
  @NonNull
  private CompletionStage<Set<String>> hidden;

  /**
   * @autogenerated HiddenFieldsGenerator
   * @return
   */
  public Optional<ColorRef> getColor() {
    return Optional.ofNullable(color);
  }
}
