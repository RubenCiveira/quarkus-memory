package org.acme.common.action;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public abstract class Slide<T> {
  private final Optional<Integer> limit;

  public Slide(Optional<Integer> limit) {
    this.limit = limit;
  }

  public abstract Slide<T> next(int limit);

  public abstract CompletableFuture<List<T>> get();

  public CompletableFuture<List<T>> filterUnitLimit(UnaryOperator<List<T>> consumer) {
    return get().thenCompose(initial -> {
      int readed = initial.size();
      boolean more = limit.map(l -> readed == l).orElse(false);
      List<T> filtered = consumer.apply(initial);
      Integer theLimit = limit.orElse(null);
      return CompletableFuture.completedFuture(filtered)
          .thenCompose(result -> (limit.isPresent() && more)
              ? fetchMorePages(this, filtered, consumer, theLimit,
                  windowSize(initial, readed, theLimit))
              : CompletableFuture.completedFuture(result));
    });
  }

  private int windowSize(List<T> initialResult, int readed, int limit) {
    int neededResults = (limit - initialResult.size());
    float aceptationRange = ((float) readed) / ((float) initialResult.size());
    return Math.max(limit, (int) (neededResults * (1 + aceptationRange)));
  }

  private CompletableFuture<List<T>> fetchMorePages(Slide<T> current, List<T> result,
      UnaryOperator<List<T>> consumer, Integer limit, int window) {
    Slide<T> slice = current.next(window);
    return slice.get().thenCompose(next -> {
      int readed = next.size();
      List<T> rest = consumer.apply(next);
      append(result, rest, limit - result.size());
      return (readed == 0 || result.size() >= limit) ? CompletableFuture.completedFuture(result)
          : fetchMorePages(slice, result, consumer, limit, windowSize(next, readed, limit));
    });
  }

  private <R> void append(List<R> fruits, List<R> next, int limit) {
    fruits.addAll(next.size() > limit ? next.subList(0, limit) : next);
  }
}
