package org.acme.features.market.fruit.domain.gateway;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

public interface FruitReadRepositoryGateway {

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param reference a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Fruit> enrich(FruitRef reference);

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Boolean> exists(String uid, Optional<FruitFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ListTraitGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  CompletionStage<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Optional<Fruit>> retrieve(String uid, Optional<FruitFilter> filter);
}
