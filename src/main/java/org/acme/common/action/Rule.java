package org.acme.common.action;

import java.util.Optional;
import lombok.Getter;

public interface Rule<T, K, R> extends Pipe<Rule.Change<T,R>, K> {
  @Getter
  public static class Change<T,R> {
    private final T value;
    private final Optional<R> original;
    
    public Change(T value) {
      this.value = value;
      this.original = Optional.empty();
    }
    
    public Change(T value, R original) {
      this.value = value;
      this.original = Optional.of( original );
    }
  }
  
//  boolean supports(K actionType);
//
//  Change<T,R> apply(Change<T,R> input, K actionType, Supplier<Change<T,R>> next);

}
