package org.acme.features.market.color.application.usecase.update;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.domain.Colors;
import org.acme.features.market.color.domain.gateway.ColorCacheGateway;
import org.acme.features.market.color.domain.gateway.ColorWriteRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateColorUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Colors aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final ColorCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<ColorUpdateEvent> eventEmitter;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final ColorWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<ColorUpdateProposal> proposalEmitter;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<ColorUpdateAllowProposal> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final ColorRef reference) {
    return ColorUpdateAllowProposal.resolveWith(updateAllow,
        ColorUpdateAllowProposal.build(query, Optional.of(reference), true, "Allowed by default"));
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return ColorUpdateAllowProposal.resolveWith(updateAllow,
        ColorUpdateAllowProposal.build(query, Optional.empty(), true, "Allowed by default"));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public ColorDto update(final ColorUpdateCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Color original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    ColorDto filled = visibility.copyWithFixed(command, command.getDto());
    ColorDto dto = ColorUpdateProposal.resolveWith(proposalEmitter, command, filled, original);
    Color saved = gateway.update(original,
        aggregate.modify(original, dto.toEntityBuilder(Optional.of(original))));
    cache.update(saved);
    ColorUpdateEvent.notifyWith(eventEmitter, command, saved);
    return visibility.copyWithHidden(command, saved);
  }
}
