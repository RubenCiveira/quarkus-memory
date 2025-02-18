package org.acme.features.market.verify.application.usecase.delete;

import org.acme.common.action.Interaction;
import org.acme.features.market.verify.application.service.event.VerifyEntityEvent;
import org.acme.features.market.verify.domain.model.Verify;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VerifyDeleteEvent extends VerifyEntityEvent {

  /**
   * @autogenerated VisibleContentProposalGenerator
   * @param emitter
   * @param interaction
   * @param entity
   */
  @SuppressWarnings("unchecked")
  public static void notifyWith(final Event<? extends VerifyDeleteEvent> emitter,
      final Interaction interaction, final Verify entity) {
    ((Event<VerifyDeleteEvent>) emitter)
        .fire(VerifyDeleteEvent.builder().interaction(interaction).entity(entity).build());
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
