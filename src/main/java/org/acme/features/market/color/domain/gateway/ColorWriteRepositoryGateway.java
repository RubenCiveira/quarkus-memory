package org.acme.features.market.color.domain.gateway;

import java.util.Optional;
import java.util.function.Function;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

public interface ColorWriteRepositoryGateway {

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @param verifier a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Color create(Color entity, Function<Color, Boolean> verifier);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Color create(Color entity);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Color delete(Color entity);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param reference a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Color enrich(ColorRef reference);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  boolean exists(String uid, Optional<ColorFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  Slider<Color> list(ColorFilter filter, ColorCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Optional<Color> retrieve(String uid, Optional<ColorFilter> filter);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param reference a filter to retrieve only matching values
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Color update(ColorRef reference, Color entity);
}
