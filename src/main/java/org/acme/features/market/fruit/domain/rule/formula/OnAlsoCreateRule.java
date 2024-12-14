package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.rule.FruitActionType;
import org.acme.features.market.fruit.domain.rule.FruitRule;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;

@Priority(10)
@ApplicationScoped
public class OnAlsoCreateRule implements FruitRule {

  @Override
  public boolean supports(FruitActionType actionType) {
    return actionType.isCreate();
  }

  @Override
  public CompletableFuture<Fruit> apply(FruitActionType actionType, CompletableFuture<Fruit> input,
      UnaryOperator<CompletableFuture<Fruit>> next, Optional<Fruit> param) {
    return next.apply(input.thenApply(base -> {
      System.out.println("==>>>ALSO ESTOY en el Also create");
      return base.withNameValue("ALSO CREATED BY " + base.getNameValue());
    }));
  }
}
