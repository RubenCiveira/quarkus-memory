package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
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
  public CompletionStage<Fruit> apply(FruitActionType actionType, CompletionStage<Fruit> input,
      UnaryOperator<CompletionStage<Fruit>> next, Optional<Fruit> param) {
    return next.apply(input.thenApply(base -> {
      System.out.println("==>>>ALSO ESTOY en el Also create");
      return base.withNameValue("ALSO CREATED BY " + base.getNameValue());
    }));
  }
}
