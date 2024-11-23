package org.acme.features.fruit.domain;

import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.model.Fruit;
import org.acme.features.fruit.domain.model.FruitRef;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped

public class Fruits {
  private final FruitsRepositoryGateway gateway;

  public Fruits(FruitsRepositoryGateway gateway) {
    super();
    this.gateway = gateway;
  }

  public Uni<Fruit> enrich(FruitRef ref) {
    return null;
  }
  
  public void validate(Fruit fruit) {
    
  }
  
  public void calculate(Fruit fruit) {
    
  }
}
