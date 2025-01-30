package org.acme.common.reactive;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.acme.common.infrastructure.StreamImpl;

public interface Stream<T> {
  
  public static <T> Stream<T> empty() {
    return new StreamImpl<>();
  }
  
  public static <T> Stream<T> just(T item) {
    return new StreamImpl<>(item);
  }

  public static <T> Stream<T> just(List<T> items) {
    return new StreamImpl<>(items);
  }
  
  Stream<Boolean> isEmpty();
  
  Stream<T> peek(Consumer<T> consumer);
  
  <R> Stream<R> map(Function<T, R> mapper);

  <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);

  Stream<T> filter(Function<T, Stream<Boolean>> predicate);

  <U, R> Stream<R> combine(Stream<U> other, BiFunction<T, U, R> combiner);

  Stream<T> take(int n); // Toma los primeros `n` elementos

  Stream<T> distinct(); // Elimina duplicados

  Stream<T> distinctUntilChanged(); // Elimina duplicados consecutivos
  
  Stream<T> completed(Runnable run);

  Stream<T> completed(Supplier<Stream<Void>> run);

  <R> Stream<R> all(Function<List<T>, Stream<R>> execution);
  
  Stream<T> ifEmpty(Supplier<Stream<T>> empty);
}
