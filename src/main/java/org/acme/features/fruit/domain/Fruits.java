package org.acme.features.fruit.domain;

import java.util.List;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class Fruits {

  private final FruitsRepositoryGateway gateway;
  
  public Uni<List<Fruit>> fruits() {
    return gateway.fruits();
  }
}
