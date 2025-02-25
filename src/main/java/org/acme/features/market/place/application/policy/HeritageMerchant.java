package org.acme.features.market.place.application.policy;

import org.acme.common.security.Actor;
import org.acme.features.market.place.application.service.proposal.PlaceCalculatedProposal;
import org.acme.features.market.place.domain.model.valueobject.PlaceMerchantVO;

import jakarta.enterprise.event.Observes;

public class HeritageMerchant {

  /**
   * Constant for rOOT field
   *
   * @autogenerated FormulaPolicyGenerator
   */
  private static final String ROL_ROOT = "ROOT";

  /**
   * @autogenerated FormulaPolicyGenerator
   * @param builder
   */
  public void calculate(@Observes PlaceCalculatedProposal builder) {
    Actor actor = builder.getInteraction().getActor();
    if (!actor.hasRole(ROL_ROOT)) {
      builder.peek(
          dto -> dto.setMerchant(PlaceMerchantVO.tryFromReference(actor.getTenant().orElse(null))));
    }
  }
}
