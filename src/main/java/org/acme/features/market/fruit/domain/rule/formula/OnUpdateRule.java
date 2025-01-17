package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.UnaryOperator;

import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.rule.FruitActionType;
import org.acme.features.market.fruit.domain.rule.FruitRule;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnUpdateRule implements FruitRule {

  @Override
  public boolean supports(FruitActionType actionType) {
    return actionType.isUpdate();
  }

  @Override
  public CompletionStage<Fruit> apply(FruitActionType actionType, CompletionStage<Fruit> input,
      UnaryOperator<CompletionStage<Fruit>> next, Optional<Fruit> param) {
    return next.apply(input.thenApply(base -> {
      System.out.println("==>>>ESTOY en el On update");
      return base.withNameValue("UPDATE BY " + base.getNameValue());
    }));
  }
}
