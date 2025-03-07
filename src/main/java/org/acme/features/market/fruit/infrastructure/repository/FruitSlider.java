package org.acme.features.market.fruit.infrastructure.repository;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

class FruitSlider extends Slider<Fruit> {

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
  private final BiFunction<FruitFilter, FruitCursor, Iterator<Fruit>> gateway;

  /**
   * @autogenerated SlideGenerator
   * @param multi
   * @param limit
   * @param gateway
   * @param filter
   * @param cursor
   */
  FruitSlider(final Iterator<Fruit> multi, final int limit,
      final BiFunction<FruitFilter, FruitCursor, Iterator<Fruit>> gateway, final FruitFilter filter,
      final FruitCursor cursor) {
    super(multi, limit);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * @autogenerated SlideGenerator
   * @param fruits
   * @param limit
   * @return
   */
  @Override
  public Iterator<Fruit> next(List<Fruit> fruits, int limit) {
    Fruit last = fruits.get(fruits.size() - 1);
    FruitCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
    return gateway.apply(this.filter, cr);
  }
}
