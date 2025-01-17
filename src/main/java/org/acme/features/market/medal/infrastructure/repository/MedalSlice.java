package org.acme.features.market.medal.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.acme.common.action.Slide;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.model.Medal;

class MedalSlice extends Slide<Medal> {

  /**
   * @autogenerated SlideGenerator
   */
  private final MedalCursor cursor;

  /**
   * @autogenerated SlideGenerator
   */
  private final MedalFilter filter;

  /**
   * @autogenerated SlideGenerator
   */
  private final BiFunction<MedalFilter, MedalCursor, CompletionStage<Slide<Medal>>> gateway;

  /**
   * @autogenerated SlideGenerator
   */
  private final List<Medal> medals;

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @param medals
   * @param gateway
   * @param filter
   * @param cursor
   */
  MedalSlice(final Optional<Integer> limit, final List<Medal> medals,
      final BiFunction<MedalFilter, MedalCursor, CompletionStage<Slide<Medal>>> gateway,
      final MedalFilter filter, final MedalCursor cursor) {
    super(limit);
    this.medals = medals;
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  /**
   * medal
   *
   * @autogenerated SlideGenerator
   * @return medal
   */
  @Override
  public List<Medal> getList() {
    return medals;
  }

  /**
   * @autogenerated SlideGenerator
   * @param limit
   * @return
   */
  @Override
  public CompletionStage<Slide<Medal>> loadNext(int limit) {
    if (medals.isEmpty()) {
      return CompletableFuture.completedStage(this);
    } else {
      Medal last = medals.get(medals.size() - 1);
      MedalCursor cr = this.cursor.withSinceUid(last.getUid().getValue()).withLimit(limit);
      return gateway.apply(this.filter, cr);
    }
  }
}
