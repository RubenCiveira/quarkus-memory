package org.acme.features.market.templatefruit.domain;

import java.util.List;
import org.acme.common.action.RulePipeline;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;
import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequestScoped
public class Fruits {

  @Getter
  @RequiredArgsConstructor
  public static enum ActionType {
    CREATE(true, false), MODIFY(false, true), ACTIVATE(false, true), DEACTIVATE(false, true);

    private final boolean create;
    private final boolean update;
  }

  private final RulePipeline<Fruit, ActionType, Fruit> rules;
  private final RulePipeline<FruitBuilder, ActionType, Fruit> builders;

  public Fruits(List<FruitRule> rules, List<FruitBuilderRule> builderRules) {
    this.rules = new RulePipeline<>(ActionType.values(), rules);
    this.builders = new RulePipeline<>(ActionType.values(), builderRules);
  }

  public Fruit initilize(FruitBuilder value) {
    Fruit entity = this.builders.ruleApply(ActionType.CREATE, value).build();
    return rules.ruleApply(ActionType.CREATE, entity);
  }

  public Fruit modify(Fruit base, FruitBuilder value) {
    Fruit entity = this.builders.ruleApply(ActionType.MODIFY, value, base).build();
    return rules.ruleApply(ActionType.MODIFY, entity, base);
  }
}
