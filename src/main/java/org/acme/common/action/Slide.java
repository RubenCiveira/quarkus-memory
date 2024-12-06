package org.acme.common.action;

import java.util.List;
import java.util.function.Function;

import io.smallrye.mutiny.Uni;

public abstract class Slide<T> {

  public abstract Uni<Slide<T>> next(int limit);

  public abstract Uni<List<T>> get();

  public <R> Uni<List<R>> filterAndFill(Integer limit, Function<List<T>, List<R>> consumer) {
    return get().flatMap(initial -> {
      int readed = initial.size();
      boolean more = limit != null && readed == limit;
      List<R> filtered = consumer.apply(initial);
      return Uni.createFrom().item(filtered)
          .flatMap(result -> (limit != null && more)
              ? fetchMorePages(this, filtered, consumer, limit, windowSize(initial, readed, limit))
              : Uni.createFrom().item(result));
    });
  }

  private int windowSize(List<T> initialResult, int readed, Integer limit) {
    int neededResults = (limit - initialResult.size());
    float aceptationRange = ((float) readed) / ((float) initialResult.size());
    // if we remove a lot of items, we ask for more items to the gateway to ensure the results
    return Math.max(limit, (int) (neededResults * (1 + aceptationRange)));
  }

  private <R> Uni<List<R>> fetchMorePages(Slide<T> current, List<R> result,
      Function<List<T>, List<R>> consumer, Integer limit, Integer window) {
    return current.next(window).flatMap(slice -> {
      return slice.get().flatMap(next -> {
        int readed = next.size();
        List<R> rest = consumer.apply(next);
        append(result, rest, limit - result.size());
        return (readed == 0 || result.size() >= limit) ? Uni.createFrom().item(result)
            : fetchMorePages(slice, result, consumer, limit, windowSize(next, readed, limit));
      });
    });
  }

  private <R> void append(List<R> fruits, List<R> next, int limit) {
    fruits.addAll(next.size() > limit ? next.subList(0, limit) : next);
  }

}
