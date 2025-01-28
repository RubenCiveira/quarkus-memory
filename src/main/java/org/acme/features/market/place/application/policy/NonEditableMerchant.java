package org.acme.features.market.place.application.policy;

import org.acme.common.security.Actor;
import org.acme.features.market.place.application.service.event.PlaceFixedFieldsPipelineStageEvent;

import jakarta.enterprise.event.Observes;

public class NonEditableMerchant {

  /**
   * Constant for rOOT field
   *
   * @autogenerated VisibilityRuleGenerator
   */
  private static final String ROL_ROOT = "ROOT";

  /**
   * @autogenerated VisibilityRuleGenerator
   * @param fixed
   */
  public void fixedFields(@Observes PlaceFixedFieldsPipelineStageEvent fixed) {
    Actor actor = fixed.getQuery().getActor();
    if (!actor.hasRole(ROL_ROOT)) {
      fixed.add("merchant");
    }
  }
}
