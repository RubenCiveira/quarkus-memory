package org.acme.common.action;

import java.util.Comparator;
import java.util.List;

public class Pipeline<T, K> {

  private final PipeRunner<T, K> runner;

  public Pipeline(K[] values, List<? extends Pipe<T, K>> rules) {
    this(values, rules, null);
  }

  public Pipeline(K[] values, List<? extends Pipe<T, K>> rules, Comparator<Pipe<T, K>> comparator) {
    runner = new PipeRunner<>(values, rules, comparator);
  }

  public T apply(K type, T initial) {
    return runner.apply(type, initial, null);
  }
}
