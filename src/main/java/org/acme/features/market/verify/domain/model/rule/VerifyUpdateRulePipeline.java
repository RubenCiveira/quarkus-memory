package org.acme.features.market.verify.domain.model.rule;

import java.util.Iterator;

import org.acme.features.market.verify.domain.model.Verify;

public interface VerifyUpdateRulePipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param verify
   * @param original
   * @return
   */
  public static Verify fire(Iterator<VerifyUpdateRulePipeline> rules, Verify verify,
      Verify original) {
    Verify result = verify;
    while (rules.hasNext()) {
      result = rules.next().updating(result, original);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param verify
   * @param original
   * @return
   */
  Verify updating(Verify verify, Verify original);
}
