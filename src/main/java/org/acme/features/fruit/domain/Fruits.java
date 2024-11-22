package org.acme.features.fruit.domain;

import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.command.FruitCreate;
import org.acme.features.fruit.domain.interaction.query.FruitList;
import org.acme.features.fruit.domain.interaction.result.FruitListResult;
import org.acme.features.fruit.domain.interaction.result.FruitReadResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class Fruits {
  private final FruitsRepositoryGateway gateway;

  public Fruits(FruitsRepositoryGateway gateway) {
    super();
    this.gateway = gateway;
  }

  public Uni<FruitListResult> fruits(FruitList query) {
    return gateway.fruits(query.getFilter(), query.getCursor())
        .map(fruits -> FruitListResult.from(query, fruits));
  }
  
  public Uni<FruitReadResult> create(FruitCreate fruit) {
    return null;
  }
}
