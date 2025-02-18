package org.acme.features.market.verify.domain.model.rule;

import java.util.Iterator;

import org.acme.features.market.verify.domain.model.Verify;

public interface VerifyCreateRulePipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param verify
   * @return
   */
  public static Verify fire(Iterator<VerifyCreateRulePipeline> rules, Verify verify) {
    Verify result = verify;
    while (rules.hasNext()) {
      result = rules.next().creating(result);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param verify
   * @return
   */
  Verify creating(Verify verify);
}
