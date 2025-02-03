package org.acme.features.market.place.application.usecase.photo.retrieve;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.domain.model.PlaceRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PlaceRetrieveUploadPhotoQuery extends Interaction {

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final PlaceRef reference;
}
