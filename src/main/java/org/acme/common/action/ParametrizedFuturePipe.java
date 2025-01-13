package org.acme.common.action;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ParametrizedFuturePipe<T, K, R>
    extends ParametrizedPipeline<CompletionStage<T>, K, R> {

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<CompletionStage<T>, K, R>> rules) {
    super(values, rules);
  }

  public ParametrizedFuturePipe(K[] values,
      List<? extends ParametrizedPipe<CompletionStage<T>, K, R>> rules,
      Comparator<ParametrizedPipe<CompletionStage<T>, K, R>> comparator) {
    super(values, rules, comparator);
  }

  public CompletionStage<T> applyCurrent(K type, T initial, R param) {
    return super.apply(type, CompletableFuture.completedFuture(initial), param);
  }
}
