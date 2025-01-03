package org.acme.common.action;

import java.util.Comparator;
import java.util.List;

public class ParametrizedPipeline<T, K, R> {

  private final PipeRunner<T, K> runner;

  public ParametrizedPipeline(K[] values, List<? extends ParametrizedPipe<T, K, R>> rules) {
    this(values, rules, null);
  }

  public ParametrizedPipeline(K[] values, List<? extends ParametrizedPipe<T, K, R>> rules,
      Comparator<ParametrizedPipe<T, K, R>> comparator) {
    runner = new PipeRunner<>(values, rules, comparator);
  }

  public T apply(K type, T initial, R param) {
    return runner.apply(type, initial, new Object[] {param});
  }
}
