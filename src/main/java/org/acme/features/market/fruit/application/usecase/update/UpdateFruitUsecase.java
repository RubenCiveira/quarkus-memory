package org.acme.features.market.fruit.application.usecase.update;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitWriteRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateFruitUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Fruits aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitCacheGateway cache;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<FruitUpdateAllowPipelineEvent> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final FruitRef reference) {
    FruitUpdateAllowPipelineEvent base = FruitUpdateAllowPipelineEvent.build(query,
        Optional.of(reference), true, "Allowed by default");
    updateAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    FruitUpdateAllowPipelineEvent base =
        FruitUpdateAllowPipelineEvent.build(query, Optional.empty(), true, "Allowed by default");
    updateAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public FruitDto update(final FruitUpdateCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    FruitDto dto = visibility.copyWithFixed(command, command.getDto());
    Fruit original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    Fruit saved = gateway.update(original,
        aggregate.modify(original, dto.toEntityBuilder(Optional.of(original))));
    cache.update(saved);
    return visibility.copyWithHidden(command, saved);
  }
}
