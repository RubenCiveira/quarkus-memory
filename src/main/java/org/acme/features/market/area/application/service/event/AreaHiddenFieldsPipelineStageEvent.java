package org.acme.features.market.area.application.service.event;

import java.util.Optional;

import org.acme.common.security.PropertiesPipelineStageEvent;
import org.acme.features.market.area.domain.model.AreaRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder(toBuilder = true)
public class AreaHiddenFieldsPipelineStageEvent extends PropertiesPipelineStageEvent {

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   */
  private final AreaRef area;

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public Optional<AreaRef> getArea() {
    return Optional.ofNullable(area);
  }

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public String resourceName() {
    return "area";
  }

  /**
   * @autogenerated HiddenFieldsPipelineStageEventGenerator
   * @return
   */
  public String viewName() {
    return "view";
  }
}
