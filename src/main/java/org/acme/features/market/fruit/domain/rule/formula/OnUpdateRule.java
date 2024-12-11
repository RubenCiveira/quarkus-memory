package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
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
  public Fruit apply(FruitActionType actionType, Fruit input, UnaryOperator<Fruit> next,
      Optional<Fruit> param) {
    System.out.println("==>>>ESTOY en el On update");
    Fruit build = input.withNameValue("UPDATE BY " + input.getNameValue());
    return next.apply(build);
  }

}
