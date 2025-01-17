package org.acme.features.market.merchant.application.service.event;

import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantVisibilityQueryPipelineStageEvent {

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   */
  @NonNull
  private CompletionStage<MerchantFilter> filter;

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   * @param mapper
   */
  public void flatMap(Function<MerchantFilter, CompletionStage<MerchantFilter>> mapper) {
    filter = filter.thenCompose(mapper);
  }

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   * @param mapper
   */
  public void map(UnaryOperator<MerchantFilter> mapper) {
    filter = filter.thenApply(mapper);
  }

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   * @param mapper
   */
  public void tap(Consumer<MerchantFilter> mapper) {
    filter = filter.thenApply(filter -> {
      mapper.accept(filter);
      return filter;
    });
  }
}
