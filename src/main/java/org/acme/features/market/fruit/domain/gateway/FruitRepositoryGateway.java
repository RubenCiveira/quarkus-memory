package org.acme.features.market.fruit.domain.gateway;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

import io.smallrye.mutiny.Uni;

public interface FruitRepositoryGateway {

  /**
   * The slide with some values
   *
   * @autogenerated ListTraitGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  public Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor);
}
