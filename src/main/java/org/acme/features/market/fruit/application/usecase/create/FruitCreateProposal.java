package org.acme.features.market.fruit.application.usecase.create;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.proposal.FruitNewProposal;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class FruitCreateProposal extends FruitNewProposal {

  /**
   * @autogenerated CreateProposalGenerator
   * @param emitter
   * @param interaction
   * @param dto
   * @return
   */
  public static FruitDto resolveWith(final Event<FruitCreateProposal> emitter,
      final Interaction interaction, final FruitDto dto) {
    return resolveWith(emitter,
        FruitCreateProposal.builder().interaction(interaction).dto(dto).build());
  }
}
