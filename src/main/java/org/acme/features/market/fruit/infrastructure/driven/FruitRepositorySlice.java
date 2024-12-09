package org.acme.features.market.fruit.infrastructure.driven;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;

public class FruitRepositorySlice extends Slide<Fruit> {
  private final List<Fruit> fruits;
  private final FruitFilter filter;
  private final FruitCursor cursor;
  private final FruitRepositoryGateway gateway;

  public FruitRepositorySlice(Optional<Integer> limit, List<Fruit> fruits, FruitFilter filter,
      FruitCursor cursor, FruitRepositoryGateway gateway) {
    super(limit);
    this.fruits = new ArrayList<>(fruits);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  @Override
  public Slide<Fruit> next(int limit) {
    System.out.println("Estamos pidiente con " + limit);
    if (fruits.isEmpty()) {
      return this;
    } else {
      Fruit last = fruits.get(fruits.size() - 1);
      FruitCursor cr =
          this.cursor.toBuilder().sinceUid(last.getUid().getValue()).limit(limit).build();
      return gateway.list(this.filter, cr);
    }
  }

  @Override
  public CompletableFuture<List<Fruit>> get() {
    return CompletableFuture.completedFuture(fruits);
  }
}
