package org.acme.common.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.acme.common.reactive.Stream;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.MultiEmitter;

public class StreamImpl<T> implements Stream<T> {
  protected final Multi<T> multi;
  private MultiEmitter<? super T> emitterReference = null;

  public StreamImpl() {
    this(false);
  }
  
  protected StreamImpl(boolean useEmitter) {
    multi = useEmitter ? Multi.createFrom().emitter(emitter -> {
      emitterReference = emitter;
    }) : Multi.createFrom().items();
  }
  
  public StreamImpl(Stream<T> base) {
    this( ((StreamImpl<T>)base).multi );
  }
  
  public StreamImpl(Optional<T> item) {
    this.multi = item.map( value -> Multi.createFrom().items(value) ).orElseGet(() -> Multi.createFrom().items() );
  }
  
  public StreamImpl(T item) {
    this.multi = Multi.createFrom().items(item);
  }

  public StreamImpl(List<T> items) {
    this.multi = Multi.createFrom().items(items.stream());
  }

  public StreamImpl(Uni<T> uni) {
    this.multi = uni.toMulti();
  }

  public StreamImpl(Multi<T> multi) {
    this.multi = multi;
  }

  @Override
  public Stream<Boolean> isEmpty() {
    return new StreamImpl<>( multi.collect().asList().map(list -> list.isEmpty()) );
  }

  @Override
  public <R> Stream<R> map(Function<T, R> mapper) {
    return copy(multi.map(mapper));
  }
  
  @Override
  public Stream<T> peek(Consumer<T> consumer) {
    return copy( multi.invoke(consumer) );
  }

  @Override
  public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
    return copy(multi.flatMap(val -> ((StreamImpl<R>) mapper.apply(val)).multi));
  }

  @Override
  public Stream<T> distinct() {
    return copy(multi.select().distinct());
  }

  @Override
  public Stream<T> distinctUntilChanged() {
    return copy(multi.skip().repetitions());
  }

  @Override
  public Stream<T> take(int n) {
    return copy(multi.select().first(n));
  }
  
  @Override
  public <R> Stream<R> all(Function<List<T>, Stream<R>> execution) {
    return null;
  }

  @Override
  public Stream<T> filter(Function<T, Stream<Boolean>> predicate) {
    return copy(multi.flatMap( value -> {
      StreamImpl<Boolean> apply = (StreamImpl<Boolean>)predicate.apply(value);
      return apply.multi.flatMap(include -> include ? Multi.createFrom().item(value) :
        Multi.createFrom().empty() );
    }) );
  }

  @Override
  public Stream<T> completed(Runnable run) {
    return copy(multi.onCompletion().call(() -> {
      run.run();
      return Uni.createFrom().voidItem();
    }));
  }
  
  @Override
  public Stream<T> completed(Supplier<Stream<Void>> run) {
    return copy(multi.onCompletion().call(() -> {
      StreamImpl<Void> imp = (StreamImpl<Void>)run.get();
      return imp.multi.toUni();
    }));
  }
  
  @Override
  public <U, R> Stream<R> combine(Stream<U> other, BiFunction<T, U, R> combiner) {
    return copy(Multi.createBy().combining().streams(this.multi, ((StreamImpl<U>) other).multi)
        .using(combiner));
  }
  
  @Override
  public Stream<T> ifEmpty(Supplier<Stream<T>> empty) {
    @SuppressWarnings("unchecked")
    Supplier<Flow.Publisher<? extends T>>  supplier = () -> ((StreamImpl<T>)empty).multi;
    return copy( multi.onCompletion().ifEmpty().switchTo(supplier) );
  }
  
  public Stream<Void> ignoreContent() {
    return copy(multi.flatMap(any -> Uni.createFrom().voidItem().toMulti() ));
  }

  protected <R> Stream<R> copy(Multi<R> from) {
    return new StreamImpl<>(from);
  }
  
  protected void close() {
    emitterReference.complete();
  }
  
  protected void next(T item) {
    emitterReference.emit(item);
  }
  
  protected void next(List<T> items) {
    items.forEach(emitterReference::emit);
  }
  
  protected void complete(T item) {
    emitterReference.emit(item);
    emitterReference.complete();
  }
  
  protected void complete(List<T> items) {
    items.forEach(emitterReference::emit);
    emitterReference.complete();
  }
  
}
