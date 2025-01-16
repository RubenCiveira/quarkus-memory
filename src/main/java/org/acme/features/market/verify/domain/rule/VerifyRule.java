package org.acme.features.market.verify.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.verify.domain.model.Verify;

public interface VerifyRule
    extends ParametrizedPipe<CompletionStage<Verify>, VerifyActionType, Optional<Verify>> {
}
