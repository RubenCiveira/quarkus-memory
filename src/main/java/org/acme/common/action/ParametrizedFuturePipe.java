package org.acme.common.action;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ParametrizedFuturePipe<T, K, R>
    extends ParametrizedPipeline<CompletableFuture<T>, K, R> {

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<CompletableFuture<T>, K, R>> rules) {
    super(values, rules);
  }

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<CompletableFuture<T>, K, R>> rules,
      Comparator<ParametrizedPipe<CompletableFuture<T>, K, R>> comparator) {
    super(values, rules, comparator);
  }

  public CompletableFuture<T> applyCurrent(K type, T initial, R param) {
    return super.apply(type, CompletableFuture.completedFuture(initial), param);
  }

}
