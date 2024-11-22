package org.acme.features.fruit.domain.interaction;

import java.util.List;
import java.util.stream.Stream;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;

public interface FruitSlice {
  Uni<FruitSlice> next(int limit);

  List<Fruit> getFruits();

  default Stream<Fruit> stream() {
    return getFruits().stream();
  }
}
