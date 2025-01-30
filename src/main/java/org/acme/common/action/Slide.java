package org.acme.common.action;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@Deprecated
public abstract class Slide<T> {
  private final Optional<Integer> limit;

  public Slide(Optional<Integer> limit) {
    this.limit = limit;
  }

  public abstract CompletionStage<Slide<T>> loadNext(int limit);

  public abstract List<T> getList();

  public CompletionStage<List<T>> filterAndFillAgain(
      Function<List<T>, CompletionStage<List<T>>> consumer) {
    List<T> initial = getList();
    int readed = initial.size();
    boolean more = limit.map(l -> readed == l).orElse(false);
    return consumer.apply(initial).thenCompose(filtered -> {
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

  private CompletionStage<List<T>> fetchMorePages(Slide<T> current, List<T> result,
      Function<List<T>, CompletionStage<List<T>>> consumer, Integer limit, int window) {
    return current.loadNext(window).thenCompose(slice -> {
      List<T> next = slice.getList();
      int readed = next.size();
      return consumer.apply(next).thenCompose(rest -> {
        append(result, rest, limit - result.size());
        return (readed == 0 || result.size() >= limit) ? CompletableFuture.completedFuture(result)
            : fetchMorePages(slice, result, consumer, limit, windowSize(next, readed, limit));
      });
    });
  }

  private <R> void append(List<R> fruits, List<R> next, int limit) {
    fruits.addAll(next.size() > limit ? next.subList(0, limit) : next);
  }
}
