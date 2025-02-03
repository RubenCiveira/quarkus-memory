package org.acme.features.market.medal.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.medal.domain.model.Medal;

public interface MedalRule extends ParametrizedPipe<Medal, MedalActionType, Optional<Medal>> {
}
