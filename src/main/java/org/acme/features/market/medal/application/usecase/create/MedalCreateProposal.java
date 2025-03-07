package org.acme.features.market.medal.application.usecase.create;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.proposal.MedalNewProposal;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MedalCreateProposal extends MedalNewProposal {

  /**
   * @autogenerated CreateProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @return
   */
  public static MedalDto resolveWith(final Event<MedalCreateProposal> emitter,
      final Interaction interaction, final MedalDto dto) {
    return resolveWith(emitter,
        MedalCreateProposal.builder().interaction(interaction).dto(dto).build());
  }
}
