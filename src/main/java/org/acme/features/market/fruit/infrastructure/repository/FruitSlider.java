package org.acme.features.market.fruit.infrastructure.repository;

import java.util.List;
import java.util.function.BiFunction;
import org.acme.common.infrastructure.SliderImpl;
import org.acme.common.reactive.Stream;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

class FruitSlider extends SliderImpl<Fruit> {
  private final FruitCursor cursor;
  private final FruitFilter filter;

  private final BiFunction<FruitFilter, FruitCursor, SliderImpl<Fruit>> gateway;

  public FruitSlider(Stream<Fruit> multi, int limit, 
      BiFunction<FruitFilter, FruitCursor, SliderImpl<Fruit>> gateway,
      final FruitFilter filter, final FruitCursor cursor) {
    super(multi, limit);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  @Override
  public SliderImpl<Fruit> next(List<Fruit> fruits, int limit) {
    Fruit last = fruits.get(fruits.size() - 1);
    FruitCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
    return gateway.apply(this.filter, cr);
  }
}
