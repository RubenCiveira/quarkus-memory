package org.acme.features.market.merchant.application.usecase.disable;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.service.proposal.MerchantModifyProposal;
import org.acme.features.market.merchant.domain.model.Merchant;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantDisableProposal extends MerchantModifyProposal {

  /**
   * @autogenerated ActionProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @param entity
   * @return
   */
  public static MerchantDto resolveWith(final Event<MerchantDisableProposal> emitter,
      final Interaction interaction, final MerchantDto dto, final Merchant entity) {
    return resolveWith(emitter,
        MerchantDisableProposal.builder().interaction(interaction).dto(dto).entity(entity).build());
  }
}
