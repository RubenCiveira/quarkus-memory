package org.acme.features.market.color.application.usecase.create;

import org.acme.common.action.Interaction;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.service.proposal.ColorNewProposal;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ColorCreateProposal extends ColorNewProposal {

  /**
   * @autogenerated CreateProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @return
   */
  public static ColorDto resolveWith(final Event<ColorCreateProposal> emitter,
      final Interaction interaction, final ColorDto dto) {
    return resolveWith(emitter,
        ColorCreateProposal.builder().interaction(interaction).dto(dto).build());
  }
}
