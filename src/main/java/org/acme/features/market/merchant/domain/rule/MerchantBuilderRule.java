package org.acme.features.market.merchant.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.Merchant.MerchantBuilder;

public interface MerchantBuilderRule extends
    ParametrizedPipe<CompletableFuture<MerchantBuilder>, MerchantActionType, Optional<Merchant>> {
}
