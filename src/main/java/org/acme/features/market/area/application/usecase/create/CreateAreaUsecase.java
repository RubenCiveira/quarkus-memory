package org.acme.features.market.area.application.usecase.create;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.area.application.AreaDto;
import org.acme.features.market.area.application.service.AreasVisibilityService;
import org.acme.features.market.area.domain.Areas;
import org.acme.features.market.area.domain.gateway.AreaCacheGateway;
import org.acme.features.market.area.domain.gateway.AreaWriteRepositoryGateway;
import org.acme.features.market.area.domain.model.Area;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreateAreaUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Areas aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final AreaCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<AreaCreateAllowPipelineEvent> createAllow;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final AreaWriteRepositoryGateway gateway;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final AreasVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    AreaCreateAllowPipelineEvent base =
        AreaCreateAllowPipelineEvent.build(query, true, "Allowed by default");
    createAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public AreaDto create(final AreaCreateCommand query) {
    Allow detail = allow(query);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    AreaDto dto = visibility.copyWithFixed(query, query.getDto());
    Area entity = aggregate.initialize(dto.toEntityBuilder(Optional.empty()));
    Area created =
        gateway.create(entity, attempt -> visibility.checkVisibility(query, attempt.getUidValue()));
    cache.update(created);
    return visibility.copyWithHidden(query, created);
  }
}
