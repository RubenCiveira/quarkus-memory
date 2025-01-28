package org.acme.features.market.fruit.application.service.event;

import java.util.Optional;

import org.acme.common.security.PropertiesPipelineStageEvent;
import org.acme.features.market.fruit.domain.model.FruitRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder(toBuilder = true)
public class FruitHiddenFieldsPipelineStageEvent extends PropertiesPipelineStageEvent {

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   */
  private final FruitRef fruit;

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public Optional<FruitRef> getFruit() {
    return Optional.ofNullable(fruit);
  }

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public String resourceName() {
    return "fruit";
  }

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public String viewName() {
    return "view";
  }
}
