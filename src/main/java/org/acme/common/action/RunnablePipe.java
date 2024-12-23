package org.acme.common.action;

import java.util.function.UnaryOperator;

public interface RunnablePipe<T, K> {
  default boolean supports(K actionType) {
    return true;
  }

  T apply(K actionType, T input, UnaryOperator<T> next, Object[] params);

}
