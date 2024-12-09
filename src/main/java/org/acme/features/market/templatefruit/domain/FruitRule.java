package org.acme.features.market.templatefruit.domain;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.templatefruit.domain.Fruits.ActionType;

public interface FruitRule extends ParametrizedPipe<Fruit, ActionType, Fruit> {
}
