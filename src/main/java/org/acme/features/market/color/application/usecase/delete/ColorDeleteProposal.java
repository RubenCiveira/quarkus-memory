package org.acme.features.market.color.application.usecase.delete;

import org.acme.common.action.Interaction;
import org.acme.features.market.color.application.service.proposal.ColorRemoveProposal;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ColorDeleteProposal extends ColorRemoveProposal {

  /**
   * @autogenerated DeleteProposalGenerator
   * @param emitter
   * @param interaction
   * @param reference
   * @return
   */
  public static ColorRef resolveWith(final Event<ColorDeleteProposal> emitter,
      final Interaction interaction, final ColorRef reference) {
    return resolveWith(emitter,
        ColorDeleteProposal.builder().interaction(interaction).reference(reference).build());
  }
}
