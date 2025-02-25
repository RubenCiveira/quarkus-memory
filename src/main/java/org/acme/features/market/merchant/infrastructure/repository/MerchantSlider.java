package org.acme.features.market.merchant.infrastructure.repository;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.model.Merchant;

class MerchantSlider extends Slider<Merchant> {

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
  private final BiFunction<MerchantFilter, MerchantCursor, Iterator<Merchant>> gateway;

  /**
   * @autogenerated SlideGenerator
   * @param multi
   * @param limit
   * @param gateway
   * @param filter
   * @param cursor
   */
  MerchantSlider(final Iterator<Merchant> multi, final int limit,
      final BiFunction<MerchantFilter, MerchantCursor, Iterator<Merchant>> gateway,
      final MerchantFilter filter, final MerchantCursor cursor) {
    super(multi, limit);
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * @autogenerated SlideGenerator
   * @param merchants
   * @param limit
   * @return
   */
  @Override
  public Iterator<Merchant> next(List<Merchant> merchants, int limit) {
    Merchant last = merchants.get(merchants.size() - 1);
    MerchantCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
    return gateway.apply(this.filter, cr);
  }
}
