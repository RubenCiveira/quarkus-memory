package org.acme.common.action;

import java.util.function.UnaryOperator;

public interface ParametrizedPipe<T, K, R> extends RunnablePipe<T, K> {
  @SuppressWarnings("unchecked")
  default T apply(K actionType, T input, UnaryOperator<T> next, Object[] params) {
    return apply(actionType, input, next, (R) params[0]);
  }

  T apply(K actionType, T input, UnaryOperator<T> next, R param);
}
