package org.acme.features.market.verify.domain.model.rule;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.Verify.VerifyBuilder;

public interface VerifyBuildFormulaPipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param verifyBuilder
   * @param original
   * @return
   */
  public static VerifyBuilder fire(Iterator<VerifyBuildFormulaPipeline> rules,
      VerifyBuilder verifyBuilder, Optional<Verify> original) {
    VerifyBuilder result = verifyBuilder;
    while (rules.hasNext()) {
      result = rules.next().calculate(result, original);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param verifyBuilder
   * @param original
   * @return
   */
  VerifyBuilder calculate(VerifyBuilder verifyBuilder, Optional<Verify> original);

  /**
   * @autogenerated AggregateGenerator
   * @return
   */
  Set<String> fields();
}
