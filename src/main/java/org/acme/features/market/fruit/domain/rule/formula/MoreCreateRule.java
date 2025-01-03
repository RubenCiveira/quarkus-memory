package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.rule.FruitActionType;
import org.acme.features.market.fruit.domain.rule.FruitRule;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MoreCreateRule implements FruitRule {

  @Override
  public boolean supports(FruitActionType actionType) {
    return actionType.isCreate();
  }

  @Override
  public CompletableFuture<Fruit> apply(FruitActionType actionType, CompletableFuture<Fruit> input,
      UnaryOperator<CompletableFuture<Fruit>> next, Optional<Fruit> param) {
    return next.apply(input.thenApply(base -> {
      System.out.println("==>>>ALSO ESTOY en el More create");
      return base.withNameValue("MORE BY " + base.getNameValue());
    }));
  }

}
