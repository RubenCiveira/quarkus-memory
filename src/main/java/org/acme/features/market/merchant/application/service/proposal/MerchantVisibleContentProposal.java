package org.acme.features.market.merchant.application.service.proposal;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.domain.model.Merchant;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantVisibleContentProposal {

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private Merchant entity;

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private Boolean visible;
}
