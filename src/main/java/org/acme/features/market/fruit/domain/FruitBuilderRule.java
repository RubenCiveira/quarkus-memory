package org.acme.features.market.fruit.domain;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.fruit.domain.Fruits.ActionType;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;

public interface FruitBuilderRule extends ParametrizedPipe<FruitBuilder, ActionType, Fruit> {
}
