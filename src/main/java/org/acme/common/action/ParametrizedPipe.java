package org.acme.common.action;

import java.util.Optional;

import lombok.Getter;

public interface ParametrizedPipe<T, K, R> extends Pipe<ParametrizedPipe.Parametrized<T, R>, K> {
  @Getter
  public static class Parametrized<T, R> {
    private final T value;
    private final Optional<R> original;

    public Parametrized(T value) {
      this.value = value;
      this.original = Optional.empty();
    }

    public Parametrized(T value, R original) {
      this.value = value;
      this.original = Optional.of(original);
    }
  }

}
