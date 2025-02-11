package org.acme.features.market.fruit.domain.gateway;

import java.util.Optional;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

public interface FruitReadRepositoryGateway {

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param reference a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Fruit enrich(FruitRef reference);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  boolean exists(String uid, Optional<FruitFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  Slider<Fruit> list(FruitFilter filter, FruitCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Optional<Fruit> retrieve(String uid, Optional<FruitFilter> filter);
}
