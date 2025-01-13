package org.acme.features.market.color.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.Color.ColorBuilder;

public interface ColorBuilderRule
    extends ParametrizedPipe<CompletionStage<ColorBuilder>, ColorActionType, Optional<Color>> {
}
