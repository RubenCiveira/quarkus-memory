package org.acme.features.fruit.infrastructure.driven;

import java.util.List;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import org.acme.features.fruit.domain.model.Fruit;

public class FruitRepositorySlice implements FruitSlice {
  private final List<Fruit> fruits;
  private final FruitsRepositoryGateway gateway;

  public FruitRepositorySlice(List<Fruit> fruits, FruitsRepositoryGateway gateway) {
    super();
    this.fruits = fruits;
    this.gateway = gateway;
  }

  @Override
  public FruitSlice next() {
    // FIXME avanzarlo
    return this;
  }

  @Override
  public List<Fruit> getFruits() {
    return fruits;
  }
}
