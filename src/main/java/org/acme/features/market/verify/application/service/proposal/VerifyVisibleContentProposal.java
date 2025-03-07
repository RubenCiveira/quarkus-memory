package org.acme.features.market.verify.application.service.proposal;

import org.acme.common.action.Interaction;
import org.acme.features.market.verify.domain.model.Verify;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VerifyVisibleContentProposal {

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private Verify entity;

  /**
   * @autogenerated VisibleContentProposalGenerator
   */
  @NonNull
  private Boolean visible;
}
