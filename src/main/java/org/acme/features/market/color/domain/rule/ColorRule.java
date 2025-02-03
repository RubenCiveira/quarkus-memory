package org.acme.features.market.color.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.color.domain.model.Color;

public interface ColorRule extends ParametrizedPipe<Color, ColorActionType, Optional<Color>> {
}
