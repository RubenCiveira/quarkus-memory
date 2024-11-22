package org.acme.features.fruit.infrastructure.driven;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class FruitRepository implements FruitsRepositoryGateway {
  private List<Fruit> fruits = new ArrayList<>();

  public FruitRepository() {
    List<String> names = List.of("rojo", "verde", "azul", "amarillo");
    for (int i = 0; i < 100; i++) {
      fruits.add(
          Fruit.builder().uid(String.format("%04d", i)).name(names.get(i % names.size())).build());
    }
  }

  public Uni<FruitSlice> fruits(FruitFilter filter, FruitCursor cursor) {
    Stream<Fruit> tfruits = fruits.stream();
    if (null != cursor.getSinceUid()) {
    }
    if (null != cursor.getLimit()) {
      tfruits = tfruits.limit(cursor.getLimit());
    }
    return Uni.createFrom().item(new FruitRepositorySlice(tfruits.toList(), this));
  }
}
