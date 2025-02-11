package org.acme.features.market.medal.application.usecase.create;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.MedalsVisibilityService;
import org.acme.features.market.medal.domain.Medals;
import org.acme.features.market.medal.domain.gateway.MedalCacheGateway;
import org.acme.features.market.medal.domain.gateway.MedalWriteRepositoryGateway;
import org.acme.features.market.medal.domain.model.Medal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreateMedalUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Medals aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MedalCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<MedalCreateAllowProposal> createAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<MedalCreateEvent> eventEmitter;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MedalWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<MedalCreateProposal> proposalEmitter;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MedalsVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return MedalCreateAllowProposal.resolveWith(createAllow,
        MedalCreateAllowProposal.build(query, true, "Allowed by default"));
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public MedalDto create(final MedalCreateCommand query) {
    Allow detail = allow(query);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    MedalDto filled = visibility.copyWithFixed(query, query.getDto());
    MedalDto dto = MedalCreateProposal.resolveWith(proposalEmitter, query, filled);
    Medal entity = aggregate.initialize(dto.toEntityBuilder(Optional.empty()));
    Medal created =
        gateway.create(entity, attempt -> visibility.checkVisibility(query, attempt.getUidValue()));
    cache.update(created);
    MedalCreateEvent.notifyWith(eventEmitter, query, created);
    return visibility.copyWithHidden(query, created);
  }
}
