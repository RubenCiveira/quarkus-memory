package org.acme.features.market.medal.application.service.event;

import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.domain.gateway.MedalFilter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MedalVisibilityQuery extends Interaction {

  /**
   * @autogenerated VisibleContentGenerator
   */
  @NonNull
  private CompletionStage<MedalFilter> filter;

  /**
   * @autogenerated VisibleContentGenerator
   * @param mapper
   */
  public void flatMap(Function<MedalFilter, CompletionStage<MedalFilter>> mapper) {
    filter = filter.thenCompose(mapper);
  }

  /**
   * @autogenerated VisibleContentGenerator
   * @param mapper
   */
  public void map(UnaryOperator<MedalFilter> mapper) {
    filter = filter.thenApply(mapper);
  }

  /**
   * @autogenerated VisibleContentGenerator
   * @param mapper
   */
  public void tap(Consumer<MedalFilter> mapper) {
    filter = filter.thenApply(filter -> {
      mapper.accept(filter);
      return filter;
    });
  }
}
