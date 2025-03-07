package org.acme.features.market.area.application.usecase.update;

import org.acme.common.action.Interaction;
import org.acme.features.market.area.application.AreaDto;
import org.acme.features.market.area.application.service.proposal.AreaModifyProposal;
import org.acme.features.market.area.domain.model.Area;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AreaUpdateProposal extends AreaModifyProposal {

  /**
   * @autogenerated UpdateProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @param entity
   * @return
   */
  public static AreaDto resolveWith(final Event<AreaUpdateProposal> emitter,
      final Interaction interaction, final AreaDto dto, final Area entity) {
    return resolveWith(emitter,
        AreaUpdateProposal.builder().interaction(interaction).dto(dto).entity(entity).build());
  }
}
