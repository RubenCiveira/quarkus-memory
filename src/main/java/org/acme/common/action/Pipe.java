package org.acme.common.action;

import java.util.function.UnaryOperator;

public interface Pipe<T, K> extends RunnablePipe<T, K> {
  default T apply(K actionType, T input, UnaryOperator<T> next, Object[] params) {
    return apply(actionType, input, next);
  }

  T apply(K actionType, T input, UnaryOperator<T> next);
}
