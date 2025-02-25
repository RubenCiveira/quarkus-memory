package org.acme.features.market.medal.domain.model.rule;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.Medal.MedalBuilder;

public interface MedalBuildFormulaPipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param medalBuilder
   * @param original
   * @return
   */
  public static MedalBuilder fire(Iterator<MedalBuildFormulaPipeline> rules,
      MedalBuilder medalBuilder, Optional<Medal> original) {
    MedalBuilder result = medalBuilder;
    while (rules.hasNext()) {
      result = rules.next().calculate(result, original);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param medalBuilder
   * @param original
   * @return
   */
  MedalBuilder calculate(MedalBuilder medalBuilder, Optional<Medal> original);

  /**
   * @autogenerated AggregateGenerator
   * @return
   */
  Set<String> fields();
}
