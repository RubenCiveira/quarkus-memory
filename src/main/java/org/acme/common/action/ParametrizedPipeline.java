package org.acme.common.action;

import java.util.List;

import org.acme.common.action.ParametrizedPipe.Parametrized;

public class ParametrizedPipeline<T, K, R> extends Pipeline<Parametrized<T, R>, K> {
  public ParametrizedPipeline(K[] values, List<? extends ParametrizedPipe<T, K, R>> ruleList) {
    super(values, ruleList);
  }

  public T applyWithoutParams(K type, T initial) {
    return apply(type, new Parametrized<>(initial)).getValue();
  }

  public T applyWithParams(K type, T initial, R original) {
    return apply(type, new Parametrized<>(initial, original)).getValue();
  }
}
