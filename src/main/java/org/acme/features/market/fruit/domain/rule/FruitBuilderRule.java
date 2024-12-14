package org.acme.features.market.fruit.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;

public interface FruitBuilderRule
    extends ParametrizedPipe<CompletableFuture<FruitBuilder>, FruitActionType, Optional<Fruit>> {
}
