package org.acme.features.market.fruit.domain.rule;

import java.util.function.UnaryOperator;
import org.acme.features.market.fruit.domain.FruitRule;
import org.acme.features.market.fruit.domain.Fruits.ActionType;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnUpdateRule implements FruitRule {

  @Override
  public boolean supports(ActionType actionType) {
    return actionType.isUpdate();
  }

  @Override
  public Parametrized<Fruit, Fruit> apply(Parametrized<Fruit, Fruit> input, ActionType actionType,
      UnaryOperator<Parametrized<Fruit, Fruit>> next) {
    System.out.println("==>>>ESTOY en el On update");
    Fruit build = input.getValue().toBuilder()
        .name(FruitNameVO.from("UPDATE BY " + input.getValue().getName().getValue())).build();
    return next.apply(input.with(build));
  }

}
