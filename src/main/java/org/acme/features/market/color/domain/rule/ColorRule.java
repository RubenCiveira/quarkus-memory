package org.acme.features.market.color.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.color.domain.model.Color;

public interface ColorRule
    extends ParametrizedPipe<CompletableFuture<Color>, ColorActionType, Optional<Color>> {
}
