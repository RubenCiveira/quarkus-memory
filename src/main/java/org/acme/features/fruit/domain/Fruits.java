package org.acme.features.fruit.domain;

import org.acme.common.action.Slide;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.model.Fruit;
import org.acme.features.fruit.domain.model.FruitRef;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class Fruits {
  private final FruitsRepositoryGateway gateway;

  public Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor) {
    return gateway.list(filter, cursor);
  }
  public Uni<Fruit> enrich(FruitRef ref) {
    return null;
  }
  
  public void validate(Fruit fruit) {
    
  }
  
  public void calculate(Fruit fruit) {
    
  }
}
