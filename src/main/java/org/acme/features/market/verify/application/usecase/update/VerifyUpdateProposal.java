package org.acme.features.market.verify.application.usecase.update;

import org.acme.common.action.Interaction;
import org.acme.features.market.verify.application.VerifyDto;
import org.acme.features.market.verify.application.service.proposal.VerifyModifyProposal;
import org.acme.features.market.verify.domain.model.Verify;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VerifyUpdateProposal extends VerifyModifyProposal {

  /**
   * @autogenerated UpdateProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @param entity
   * @return
   */
  public static VerifyDto resolveWith(final Event<VerifyUpdateProposal> emitter,
      final Interaction interaction, final VerifyDto dto, final Verify entity) {
    return resolveWith(emitter,
        VerifyUpdateProposal.builder().interaction(interaction).dto(dto).entity(entity).build());
  }
}
