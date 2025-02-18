package org.acme.features.market.fruit.application.service.proposal;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.application.FruitDto;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class FruitNewProposal {

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param emitter
   * @param proposal
   * @return
   */
  @SuppressWarnings("unchecked")
  public static FruitDto resolveWith(final Event<? extends FruitNewProposal> emitter,
      final FruitNewProposal proposal) {
    ((Event<FruitNewProposal>) emitter).fire(proposal);
    return proposal.getDto();
  }

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private FruitDto dto;

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param mapper
   */
  public void map(UnaryOperator<FruitDto> mapper) {
    dto = mapper.apply(dto);
  }

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param mapper
   */
  public void peek(Consumer<FruitDto> mapper) {
    mapper.accept(dto);
  }
}
