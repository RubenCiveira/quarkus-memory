package org.acme.features.market.verify.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
  private final BiFunction<VerifyFilter, VerifyCursor, CompletionStage<Slide<Verify>>> gateway;

  /**
   * @autogenerated SlideGenerator
   */
  private final List<Verify> verifys;

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @param verifys
   * @param gateway
   * @param filter
   * @param cursor
   */
  VerifySlice(final Optional<Integer> limit, final List<Verify> verifys,
      final BiFunction<VerifyFilter, VerifyCursor, CompletionStage<Slide<Verify>>> gateway,
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
  public List<Verify> getList() {
    return verifys;
  }

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @return
   */
  @Override
  public CompletionStage<Slide<Verify>> loadNext(int limit) {
    if (verifys.isEmpty()) {
      return CompletableFuture.completedStage(this);
    } else {
      Verify last = verifys.get(verifys.size() - 1);
      VerifyCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
      return gateway.apply(this.filter, cr);
    }
  }
}