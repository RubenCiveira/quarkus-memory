package org.acme.features.market.place.application.interaction.visibility;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.domain.model.PlaceRef;

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
public class PlaceHiddenFields extends Interaction {

  /**
   * @autogenerated HiddenFieldsGenerator
   */
  private final PlaceRef place;

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
  public Optional<PlaceRef> getPlace() {
    return Optional.ofNullable(place);
  }
}
