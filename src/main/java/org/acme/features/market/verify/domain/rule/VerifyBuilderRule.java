package org.acme.features.market.verify.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.Verify.VerifyBuilder;

public interface VerifyBuilderRule
    extends ParametrizedPipe<VerifyBuilder, VerifyActionType, Optional<Verify>> {
}
