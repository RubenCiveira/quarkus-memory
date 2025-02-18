package org.acme.features.market.merchant.application.usecase.delete;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.service.event.MerchantEntityEvent;
import org.acme.features.market.merchant.domain.model.Merchant;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantDeleteEvent extends MerchantEntityEvent {

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param emitter
   * @param interaction
   * @param entity
   */
  @SuppressWarnings("unchecked")
  public static void notifyWith(final Event<? extends MerchantDeleteEvent> emitter,
      final Interaction interaction, final Merchant entity) {
    ((Event<MerchantDeleteEvent>) emitter)
        .fire(MerchantDeleteEvent.builder().interaction(interaction).entity(entity).build());
  }

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @return
   */
  @Override
  public String operation() {
    return "delete";
  }
}
