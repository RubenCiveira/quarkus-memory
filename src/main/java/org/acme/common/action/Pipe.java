package org.acme.common.action;

import java.util.function.UnaryOperator;

public interface Pipe<T, K> {
  default boolean supports(K actionType) {
    return true;
  }

  T apply(T input, K actionType, UnaryOperator<T> next);

}
