package org.acme.features.market.merchant.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.acme.common.action.Slide;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.model.Merchant;

class MerchantSlice extends Slide<Merchant> {

  /**
   * @autogenerated SlideGenerator
   */
  private final MerchantCursor cursor;

  /**
   * @autogenerated SlideGenerator
   */
  private final MerchantFilter filter;

  /**
   * @autogenerated SlideGenerator
   */
  private final BiFunction<MerchantFilter, MerchantCursor, CompletionStage<Slide<Merchant>>> gateway;

  /**
   * @autogenerated SlideGenerator
   */
  private final List<Merchant> merchants;

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @param merchants
   * @param gateway
   * @param filter
   * @param cursor
   */
  MerchantSlice(final Optional<Integer> limit, final List<Merchant> merchants,
      final BiFunction<MerchantFilter, MerchantCursor, CompletionStage<Slide<Merchant>>> gateway,
      final MerchantFilter filter, final MerchantCursor cursor) {
    super(limit);
    this.merchants = merchants;
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * merchant
   *
   * @autogenerated SlideGenerator
   * @return merchant
   */
  @Override
  public List<Merchant> getList() {
    return merchants;
  }

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @return
   */
  @Override
  public CompletionStage<Slide<Merchant>> loadNext(int limit) {
    if (merchants.isEmpty()) {
      return CompletableFuture.completedStage(this);
    } else {
      Merchant last = merchants.get(merchants.size() - 1);
      MerchantCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
      return gateway.apply(this.filter, cr);
    }
  }
}
