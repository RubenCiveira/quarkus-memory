package org.acme.features.market.area.application.service.event;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.acme.common.action.Interaction;
import org.acme.features.market.area.domain.gateway.AreaFilter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AreaVisibilityQueryPipelineEvent {

  /**
   * @autogenerated VisibleContentPipelineEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentPipelineEventGenerator
   */
  @NonNull
  private AreaFilter filter;

  /**
   * @autogenerated VisibleContentPipelineEventGenerator
   * @param mapper
   */
  public void map(UnaryOperator<AreaFilter> mapper) {
    filter = mapper.apply(filter);
  }

  /**
   * @autogenerated VisibleContentPipelineEventGenerator
   * @param mapper
   */
  public void peek(Consumer<AreaFilter> mapper) {
    mapper.accept(filter);
  }
}
