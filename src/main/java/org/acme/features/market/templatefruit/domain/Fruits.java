package org.acme.features.market.templatefruit.domain;

import java.util.List;

import org.acme.common.action.ParametrizedPipeline;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitEntityBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// @RequestScoped
public class Fruits {

  @Getter
  @RequiredArgsConstructor
  public static enum ActionType {
    CREATE(true, false), MODIFY(false, true), ACTIVATE(false, true), DEACTIVATE(false, true);

    private final boolean create;
    private final boolean update;
  }

  private final ParametrizedPipeline<Fruit, ActionType, Fruit> rules;
  private final ParametrizedPipeline<FruitEntityBuilder, ActionType, Fruit> builders;

  public Fruits(List<FruitRule> rules, List<FruitBuilderRule> builderRules) {
    this.rules = new ParametrizedPipeline<>(ActionType.values(), rules);
    this.builders = new ParametrizedPipeline<>(ActionType.values(), builderRules);
  }

  public Fruit initilize(FruitEntityBuilder value) {
    Fruit entity = this.builders.applyWithoutParams(ActionType.CREATE, value).build();
    return rules.applyWithoutParams(ActionType.CREATE, entity);
  }

  public Fruit modify(Fruit base, FruitEntityBuilder value) {
    Fruit entity = this.builders.applyWithParams(ActionType.MODIFY, value, base).build();
    return rules.applyWithParams(ActionType.MODIFY, entity, base);
  }

}
