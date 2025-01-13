package org.acme.features.market.fruit.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.fruit.domain.model.Fruit;

public interface FruitRule
    extends ParametrizedPipe<CompletionStage<Fruit>, FruitActionType, Optional<Fruit>> {
}
