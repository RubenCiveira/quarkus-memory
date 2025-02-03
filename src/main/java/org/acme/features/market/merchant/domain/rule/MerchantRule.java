package org.acme.features.market.merchant.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.merchant.domain.model.Merchant;

public interface MerchantRule
    extends ParametrizedPipe<Merchant, MerchantActionType, Optional<Merchant>> {
}
