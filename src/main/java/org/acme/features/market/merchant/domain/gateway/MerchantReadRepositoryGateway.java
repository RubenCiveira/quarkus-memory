package org.acme.features.market.merchant.domain.gateway;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Slide;
import org.acme.features.market.merchant.domain.model.Merchant;

public interface MerchantReadRepositoryGateway {

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Boolean> exists(String uid, Optional<MerchantFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ListTraitGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  Slide<Merchant> list(MerchantFilter filter, MerchantCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated RetrieveTraitGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  CompletionStage<Optional<Merchant>> retrieve(String uid, Optional<MerchantFilter> filter);
}