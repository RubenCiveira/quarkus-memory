package org.acme.features.market.place.application.usecase.photo.upload;

import org.acme.common.action.Interaction;
import org.acme.common.store.BinaryContent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PlacePhotoTemporalUploadCommand extends Interaction {

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private BinaryContent binary;
}
