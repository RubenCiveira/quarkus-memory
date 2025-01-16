package org.acme.features.market.area.application.policy;

import org.acme.common.security.Actor;
import org.acme.features.market.area.application.service.event.AreaVisibilityQueryPipelineStageEvent;

import jakarta.enterprise.event.Observes;

public class MerchantAccesible {

  /**
   * Constant for rOOT field
   *
   * @autogenerated VisibilityRuleGenerator
   */
  private static final String ROL_ROOT = "ROOT";

  /**
   * @autogenerated VisibilityRuleGenerator
   * @param filter
   */
  public void filterVisibles(@Observes AreaVisibilityQueryPipelineStageEvent filter) {
    Actor actor = filter.getInteraction().getActor();
    if (!actor.hasRole(ROL_ROOT)) {
      filter.tap(fl -> fl.setPlaceMerchantMerchantAccesible(actor.getTenant().orElse(null)));
    }
  }
}