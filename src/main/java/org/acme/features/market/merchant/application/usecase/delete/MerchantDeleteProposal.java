package org.acme.features.market.merchant.application.usecase.delete;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.service.proposal.MerchantRemoveProposal;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantDeleteProposal extends MerchantRemoveProposal {

  /**
   * @autogenerated DeleteProposalGenerator
   * @param emitter
   * @param interaction
   * @param reference
   * @return
   */
  public static MerchantRef resolveWith(final Event<MerchantDeleteProposal> emitter,
      final Interaction interaction, final MerchantRef reference) {
    return resolveWith(emitter,
        MerchantDeleteProposal.builder().interaction(interaction).reference(reference).build());
  }
}
