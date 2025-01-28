package org.acme.features.market.verify.application.service.event;

import java.util.Optional;

import org.acme.common.security.PropertiesPipelineStageEvent;
import org.acme.features.market.verify.domain.model.VerifyRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder(toBuilder = true)
public class VerifyFixedFieldsPipelineStageEvent extends PropertiesPipelineStageEvent {

  /**
   * @autogenerated FixedFieldsPipelineStageEventGenerator
   */
  private final VerifyRef verify;

  /**
   * @autogenerated FixedFieldsPipelineStageEventGenerator
   * @return
   */
  public Optional<VerifyRef> getVerify() {
    return Optional.ofNullable(verify);
  }

  /**
   * @autogenerated FixedFieldsPipelineStageEventGenerator
   * @return
   */
  public String resourceName() {
    return "verify";
  }

  /**
   * @autogenerated FixedFieldsPipelineStageEventGenerator
   * @return
   */
  public String viewName() {
    return "update";
  }
}
