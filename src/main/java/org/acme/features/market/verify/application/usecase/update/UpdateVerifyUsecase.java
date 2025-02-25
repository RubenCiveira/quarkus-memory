package org.acme.features.market.verify.application.usecase.update;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.verify.application.VerifyDto;
import org.acme.features.market.verify.application.service.VerifysVisibilityService;
import org.acme.features.market.verify.domain.Verifys;
import org.acme.features.market.verify.domain.gateway.VerifyCacheGateway;
import org.acme.features.market.verify.domain.gateway.VerifyWriteRepositoryGateway;
import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.VerifyRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateVerifyUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Verifys aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final VerifyCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<VerifyUpdateEvent> eventEmitter;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final VerifyWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<VerifyUpdateProposal> proposalEmitter;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<VerifyUpdateAllowProposal> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final VerifysVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final VerifyRef reference) {
    return VerifyUpdateAllowProposal.resolveWith(updateAllow,
        VerifyUpdateAllowProposal.build(query, Optional.of(reference), true, "Allowed by default"));
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return VerifyUpdateAllowProposal.resolveWith(updateAllow,
        VerifyUpdateAllowProposal.build(query, Optional.empty(), true, "Allowed by default"));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public VerifyDto update(final VerifyUpdateCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Verify original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    VerifyDto filled = visibility.copyWithFixed(command, command.getDto());
    VerifyDto dto = VerifyUpdateProposal.resolveWith(proposalEmitter, command, filled, original);
    Verify saved = gateway.update(original,
        aggregate.modify(original, dto.toEntityBuilder(Optional.of(original))));
    cache.update(saved);
    VerifyUpdateEvent.notifyWith(eventEmitter, command, saved);
    return visibility.copyWithHidden(command, saved);
  }
}
