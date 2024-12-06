package org.acme.features.market.fruit.infrastructure.driven;

import java.util.ArrayList;
import java.util.List;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.gateway.FruitRepositoryGateway;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

import io.smallrye.mutiny.Uni;

public class FruitRepositorySlice extends Slide<Fruit> {
  private final List<Fruit> fruits;
  private final FruitFilter filter;
  private final FruitCursor cursor;
  private final FruitRepositoryGateway gateway;

  public FruitRepositorySlice(List<Fruit> fruits, FruitFilter filter, FruitCursor cursor,
      FruitRepositoryGateway gateway) {
    super();
    this.fruits = new ArrayList<>(fruits);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  @Override
  public Uni<Slide<Fruit>> next(int limit) {
    if (fruits.isEmpty()) {
      return Uni.createFrom().item(this);
    } else {
      Fruit last = fruits.get(fruits.size() - 1);
      FruitCursor cr =
          this.cursor.toBuilder().sinceUid(last.getUid().getValue()).limit(limit).build();
      return gateway.list(this.filter, cr);
    }
  }

  @Override
  public Uni<List<Fruit>> get() {
    return Uni.createFrom().item(fruits);
  }
}
