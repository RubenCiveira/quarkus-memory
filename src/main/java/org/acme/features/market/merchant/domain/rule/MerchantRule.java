package org.acme.features.market.merchant.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.merchant.domain.model.Merchant;

public interface MerchantRule
    extends ParametrizedPipe<CompletionStage<Merchant>, MerchantActionType, Optional<Merchant>> {
}
