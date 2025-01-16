package org.acme.features.market.verify.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import org.acme.common.action.Slide;
import org.acme.features.market.verify.domain.gateway.VerifyCursor;
import org.acme.features.market.verify.domain.gateway.VerifyFilter;
import org.acme.features.market.verify.domain.model.Verify;

class VerifySlice extends Slide<Verify> {

  /**
   * @autogenerated SlideGenerator
   */
  private final VerifyCursor cursor;

  /**
   * @autogenerated SlideGenerator
   */
  private final VerifyFilter filter;

  /**
   * @autogenerated SlideGenerator
   */
  private final BiFunction<VerifyFilter, VerifyCursor, Slide<Verify>> gateway;

  /**
   * @autogenerated SlideGenerator
   */
  private final CompletionStage<List<Verify>> verifys;

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @param verifys
   * @param gateway
   * @param filter
   * @param cursor
   */
  VerifySlice(final Optional<Integer> limit, final CompletionStage<List<Verify>> verifys,
      final BiFunction<VerifyFilter, VerifyCursor, Slide<Verify>> gateway,
      final VerifyFilter filter, final VerifyCursor cursor) {
    super(limit);
    this.verifys = verifys;
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * verify
   *
   * @autogenerated SlideGenerator
   * @return verify
   */
  @Override
  public CompletionStage<List<Verify>> get() {
    return verifys;
  }

  /**
   * @autogenerated SlideGenerator
   * @param list
   * @param limit
   * @return
   */
  public Slide<Verify> map(List<Verify> list, int limit) {
    if (list.isEmpty()) {
      return this;
    } else {
      Verify last = list.get(list.size() - 1);
      VerifyCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
      return gateway.apply(this.filter, cr);
    }
  }

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @return
   */
  @Override
  public Slide<Verify> next(int limit) {
    try {
      return verifys.thenApply(list -> map(list, limit)).toCompletableFuture().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new IllegalStateException("Unable to complete querying ", e);
    }
  }
}
