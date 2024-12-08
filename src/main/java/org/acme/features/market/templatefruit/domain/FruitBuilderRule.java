package org.acme.features.market.templatefruit.domain;

import org.acme.common.action.Rule;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;
import org.acme.features.market.templatefruit.domain.Fruits.ActionType;

public interface FruitBuilderRule extends Rule<FruitBuilder, ActionType, Fruit> {
}

