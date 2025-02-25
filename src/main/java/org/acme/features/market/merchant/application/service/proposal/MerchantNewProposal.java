package org.acme.features.market.merchant.application.service.proposal;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class MerchantNewProposal {

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param emitter
   * @param proposal
   * @return
   */
  @SuppressWarnings("unchecked")
  public static MerchantDto resolveWith(final Event<? extends MerchantNewProposal> emitter,
      final MerchantNewProposal proposal) {
    ((Event<MerchantNewProposal>) emitter).fire(proposal);
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
  private MerchantDto dto;

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param mapper
   */
  public void map(UnaryOperator<MerchantDto> mapper) {
    dto = mapper.apply(dto);
  }

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param mapper
   */
  public void peek(Consumer<MerchantDto> mapper) {
    mapper.accept(dto);
  }
}
