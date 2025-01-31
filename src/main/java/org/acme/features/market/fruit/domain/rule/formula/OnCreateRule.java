package org.acme.features.market.fruit.domain.rule.formula;

import java.util.Optional;
import java.util.function.UnaryOperator;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.rule.FruitActionType;
import org.acme.features.market.fruit.domain.rule.FruitRule;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;

@Priority(2)
@ApplicationScoped
public class OnCreateRule implements FruitRule {

  @Override
  public boolean supports(FruitActionType actionType) {
    return actionType.isCreate();
  }

  @Override
  public Fruit apply(FruitActionType actionType, Fruit base, UnaryOperator<Fruit> next,
      Optional<Fruit> param) {
    return next.apply(base.withNameValue("CREATED BY " + base.getNameValue()));
  }

}
