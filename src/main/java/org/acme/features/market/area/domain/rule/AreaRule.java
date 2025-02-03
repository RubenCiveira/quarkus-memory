package org.acme.features.market.area.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.area.domain.model.Area;

public interface AreaRule extends ParametrizedPipe<Area, AreaActionType, Optional<Area>> {
}
