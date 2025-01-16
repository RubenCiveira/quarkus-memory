package org.acme.features.market.fruit.application.service.event;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.domain.model.Fruit;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitVisibleContentPipelineStageEvent {

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentPipelineStageEventGenerator
   */
  @NonNull
  private CompletionStage<List<Fruit>> visibles;
}