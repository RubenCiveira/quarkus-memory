package org.acme.features.market.fruit.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;

public interface FruitBuilderRule
    extends ParametrizedPipe<CompletionStage<FruitBuilder>, FruitActionType, Optional<Fruit>> {
}
