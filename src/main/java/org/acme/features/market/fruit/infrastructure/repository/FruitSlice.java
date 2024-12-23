package org.acme.features.market.fruit.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

class FruitSlice extends Slide<Fruit> {

  /**
   * @autogenerated SlideGenerator
   */
  private final FruitCursor cursor;

  /**
   * @autogenerated SlideGenerator
   */
  private final FruitFilter filter;

  /**
   * @autogenerated SlideGenerator
   */
  private final CompletionStage<List<Fruit>> fruits;

  /**
   * @autogenerated SlideGenerator
   */
  private final BiFunction<FruitFilter, FruitCursor, Slide<Fruit>> gateway;

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @param fruits
   * @param gateway
   * @param filter
   * @param cursor
   */
  FruitSlice(final Optional<Integer> limit, final CompletionStage<List<Fruit>> fruits,
      final BiFunction<FruitFilter, FruitCursor, Slide<Fruit>> gateway, final FruitFilter filter,
      final FruitCursor cursor) {
    super(limit);
    this.fruits = fruits;
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * fruit
   *
   * @autogenerated SlideGenerator
   * @return fruit
   */
  @Override
  public CompletionStage<List<Fruit>> get() {
    return fruits;
  }

  /**
   * @autogenerated SlideGenerator
   * @param list
   * @param limit
   * @return
   */
  public Slide<Fruit> map(List<Fruit> list, int limit) {
    if (list.isEmpty()) {
      return this;
    } else {
      Fruit last = list.get(list.size() - 1);
      FruitCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
      return gateway.apply(this.filter, cr);
    }
  }

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @return
   */
  @Override
  public Slide<Fruit> next(int limit) {
    try {
      return fruits.thenApply(list -> map(list, limit)).toCompletableFuture().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new IllegalStateException("Unable to complete querying ", e);
    }
  }
}
