package org.acme.features.market.fruit.domain.gateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.fruit.domain.model.Fruit;

public interface FruitCacheGateway {

  /**
   * @autogenerated CacheGatewayGenerator
   * @param fruit
   * @return
   */
  public CompletionStage<Void> remove(Fruit fruit);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<FruitCached>> retrieve(FruitFilter filter, FruitCursor cursor);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @param fruits
   * @return
   */
  public CompletionStage<Void> store(FruitFilter filter, FruitCursor cursor, List<Fruit> fruits);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param fruit
   * @return
   */
  public CompletionStage<Void> update(Fruit fruit);
}
