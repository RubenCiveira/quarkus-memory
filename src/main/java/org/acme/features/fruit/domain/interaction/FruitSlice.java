package org.acme.features.fruit.domain.interaction;

import java.util.List;
import java.util.stream.Stream;
import org.acme.features.fruit.domain.model.Fruit;

public interface FruitSlice {
  FruitSlice next();

  List<Fruit> getFruits();

  default Stream<Fruit> stream() {
    return getFruits().stream();
  }
}
