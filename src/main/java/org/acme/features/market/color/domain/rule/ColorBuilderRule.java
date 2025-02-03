package org.acme.features.market.color.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.Color.ColorBuilder;

public interface ColorBuilderRule
    extends ParametrizedPipe<ColorBuilder, ColorActionType, Optional<Color>> {
}
