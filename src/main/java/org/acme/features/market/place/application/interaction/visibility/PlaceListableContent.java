package org.acme.features.market.place.application.interaction.visibility;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.domain.model.Place;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PlaceListableContent extends Interaction {

  /**
   * @autogenerated VisibleContentGenerator
   */
  @NonNull
  private CompletionStage<List<Place>> listables;
}
