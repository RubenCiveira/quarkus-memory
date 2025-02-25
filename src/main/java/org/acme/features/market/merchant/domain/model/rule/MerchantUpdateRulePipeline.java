package org.acme.features.market.merchant.domain.model.rule;

import java.util.Iterator;

import org.acme.features.market.merchant.domain.model.Merchant;

public interface MerchantUpdateRulePipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param merchant
   * @param original
   * @return
   */
  public static Merchant fire(Iterator<MerchantUpdateRulePipeline> rules, Merchant merchant,
      Merchant original) {
    Merchant result = merchant;
    while (rules.hasNext()) {
      result = rules.next().updating(result, original);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param merchant
   * @param original
   * @return
   */
  Merchant updating(Merchant merchant, Merchant original);
}
