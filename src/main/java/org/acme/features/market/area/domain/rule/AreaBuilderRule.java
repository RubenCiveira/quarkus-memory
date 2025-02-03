package org.acme.features.market.area.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.area.domain.model.Area;
import org.acme.features.market.area.domain.model.Area.AreaBuilder;

public interface AreaBuilderRule
    extends ParametrizedPipe<AreaBuilder, AreaActionType, Optional<Area>> {
}
