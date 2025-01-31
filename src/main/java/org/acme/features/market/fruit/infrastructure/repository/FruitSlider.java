package org.acme.features.market.fruit.infrastructure.repository;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import org.acme.common.algorithms.Slider;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

class FruitSlider extends Slider<Fruit> {
  private final FruitCursor cursor;
  private final FruitFilter filter;

  private final BiFunction<FruitFilter, FruitCursor, Iterator<Fruit>> gateway;

  public FruitSlider(Iterator<Fruit> multi, int limit, 
      BiFunction<FruitFilter, FruitCursor, Iterator<Fruit>> gateway,
      final FruitFilter filter, final FruitCursor cursor) {
    super(multi, limit);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  @Override
  public Iterator<Fruit> next(List<Fruit> fruits, int limit) {
    Fruit last = fruits.get(fruits.size() - 1);
    FruitCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
    return gateway.apply(this.filter, cr);
  }

}
