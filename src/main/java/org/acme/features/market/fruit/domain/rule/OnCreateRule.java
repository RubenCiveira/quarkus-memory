package org.acme.features.market.fruit.domain.rule;

import java.util.function.UnaryOperator;

import org.acme.features.market.fruit.domain.FruitRule;
import org.acme.features.market.fruit.domain.Fruits.ActionType;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;

@Priority(2)
@ApplicationScoped
public class OnCreateRule implements FruitRule {

  @Override
  public boolean supports(ActionType actionType) {
    return actionType.isCreate();
  }

  @Override
  public Parametrized<Fruit, Fruit> apply(Parametrized<Fruit, Fruit> input, ActionType actionType,
      UnaryOperator<Parametrized<Fruit, Fruit>> next) {
    System.out.println("==>>>ESTOY en el On create");
    Fruit build = input.getValue().toBuilder()
        .name(FruitNameVO.from("CREATED BY " + input.getValue().getName().getValue())).build();
    return next.apply(input.with(build));
  }

}
