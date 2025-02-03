package org.acme.features.market.medal.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.Medal.MedalBuilder;

public interface MedalBuilderRule
    extends ParametrizedPipe<MedalBuilder, MedalActionType, Optional<Medal>> {
}
