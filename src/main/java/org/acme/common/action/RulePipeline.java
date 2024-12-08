package org.acme.common.action;

import java.util.List;
import org.acme.common.action.Rule.Change;

public class RulePipeline<T, K, R> extends Pipeline<Change<T,R>, K>{
  public RulePipeline(K[] values, List<? extends Rule<T, K, R>> ruleList) {
    super(values, ruleList);
  }

  public T ruleApply(K type, T initial) {
    return apply(type, new Change<>(initial)).getValue();
  }
  
  public T ruleApply(K type, T initial, R original) {
    return apply(type, new Change<>(initial, original)).getValue();
  }
}
