package org.acme.features.market.color.domain.gateway;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Slide;
import org.acme.features.market.color.domain.model.Color;

public interface ColorReadRepositoryGateway {

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Boolean> exists(String uid, Optional<ColorFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ListTraitGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  Slide<Color> list(ColorFilter filter, ColorCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Optional<Color>> retrieve(String uid, Optional<ColorFilter> filter);
}
