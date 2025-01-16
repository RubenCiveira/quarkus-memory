package org.acme.features.market.area.application.usecase.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.area.application.service.AreasVisibilityService;
import org.acme.features.market.area.application.usecase.create.event.AreaCreateAllowPipelineStageEvent;
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
  private final Event<AreaCreateAllowPipelineStageEvent> createAllow;

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
  public CompletionStage<Allow> allow(final Interaction query) {
    AreaCreateAllowPipelineStageEvent base =
        AreaCreateAllowPipelineStageEvent.build(query, true, "Allowed by default");
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
  public CompletionStage<AreaCreateResult> create(final AreaCreateCommand query) {
    CompletionStage<Optional<Area>> create = allow(query).thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.copyWithFixed(query, query.getDto())
          .thenCompose(builder -> aggregate.initialize(builder.toEntityBuilder(Optional.empty()))
              .thenCompose(areaEntity -> createAndVerify(query, areaEntity)));
    });
    return create.thenCompose(area -> mapEntity(query, area));
  }

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @param areaEntity
   * @return
   */
  private CompletionStage<Optional<Area>> createAndVerify(final AreaCreateCommand query,
      final Area areaEntity) {
    return gateway
        .create(areaEntity, created -> visibility.checkVisibility(query, created.getUidValue()))
        .thenCompose(validated -> validated
            .map(created -> cache.update(created).thenApply(_ready -> Optional.of(created)))
            .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())));
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param oparea
   * @return The slide with some values
   */
  private CompletionStage<AreaCreateResult> mapEntity(final AreaCreateCommand command,
      final Optional<Area> oparea) {
    return oparea
        .map(area -> visibility.copyWithHidden(command, area)
            .thenApply(visible -> AreaCreateResult.builder().command(command)
                .area(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            AreaCreateResult.builder().command(command).area(Optional.empty()).build()));
  }
}