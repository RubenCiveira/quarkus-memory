package org.acme.features.fruit.infrastructure.driven;

import java.util.List;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class FruitRepository implements FruitsRepositoryGateway {

  public Uni<List<Fruit>> fruits() {
    List<Fruit> fruits = List.of(Fruit.builder().uid("1").name("rojo").build(),
        Fruit.builder().uid("1").name("verde").build(),
        Fruit.builder().uid("1").name("azul").build());
    return Uni.createFrom().item(fruits);
  }
}
