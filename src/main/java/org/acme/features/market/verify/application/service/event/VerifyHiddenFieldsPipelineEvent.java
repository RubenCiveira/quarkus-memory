package org.acme.features.market.verify.application.service.event;

import java.util.Optional;

import org.acme.common.security.PropertiesPipelineEvent;
import org.acme.features.market.verify.domain.model.VerifyRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VerifyHiddenFieldsPipelineEvent extends PropertiesPipelineEvent {

  /**
   * @autogenerated HiddenFieldsPipelineEventGenerator
   */
  private final VerifyRef verify;

  /**
   * @autogenerated HiddenFieldsPipelineEventGenerator
   * @return
   */
  public Optional<VerifyRef> getVerify() {
    return Optional.ofNullable(verify);
  }

  /**
   * @autogenerated HiddenFieldsPipelineEventGenerator
   * @return
   */
  public String resourceName() {
    return "verify";
  }

  /**
   * @autogenerated HiddenFieldsPipelineEventGenerator
   * @return
   */
  public String viewName() {
    return "view";
  }
}
