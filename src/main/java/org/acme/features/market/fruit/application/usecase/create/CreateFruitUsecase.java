package org.acme.features.market.fruit.application.usecase.create;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitWriteRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreateFruitUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Fruits aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final FruitCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<FruitCreateAllowProposal> createAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<FruitCreateEvent> eventEmitter;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final FruitWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<FruitCreateProposal> proposalEmitter;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return FruitCreateAllowProposal.resolveWith(createAllow,
        FruitCreateAllowProposal.build(query, true, "Allowed by default"));
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public FruitDto create(final FruitCreateCommand query) {
    Allow detail = allow(query);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    FruitDto filled = visibility.copyWithFixed(query, query.getDto());
    FruitDto dto = FruitCreateProposal.resolveWith(proposalEmitter, query, filled);
    Fruit entity = aggregate.initialize(dto.toEntityBuilder(Optional.empty()));
    Fruit created =
        gateway.create(entity, attempt -> visibility.checkVisibility(query, attempt.getUidValue()));
    cache.update(created);
    FruitCreateEvent.notifyWith(eventEmitter, query, created);
    return visibility.copyWithHidden(query, created);
  }
}
