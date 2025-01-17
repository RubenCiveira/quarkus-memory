package org.acme.features.market.fruit.application.service.event;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.domain.model.FruitRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitHiddenFieldsPipelineStageEvent {

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   */
  private final FruitRef fruit;

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * The list of hidden fields
   *
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   */
  @NonNull
  private CompletionStage<Set<String>> hidden;

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @param fields
   */
  public void add(String... fields) {
    hidden = hidden.thenApply(set -> {
      for (String field : fields) {
        set.add(field);
      }
      return set;
    });
  }

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public Optional<FruitRef> getFruit() {
    return Optional.ofNullable(fruit);
  }
}
