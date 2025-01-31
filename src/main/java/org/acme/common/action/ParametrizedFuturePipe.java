package org.acme.common.action;

import java.util.Comparator;
import java.util.List;

public class ParametrizedFuturePipe<T, K, R>
    extends ParametrizedPipeline<T, K, R> {

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<T, K, R>> rules) {
    super(values, rules);
  }

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<T, K, R>> rules,
      Comparator<ParametrizedPipe<T, K, R>> comparator) {
    super(values, rules, comparator);
  }

  public T applyCurrent(K type, T initial, R param) {
    return super.apply(type, initial, param);
  }
}
