package org.acme.features.market.area.application.usecase.update;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.area.application.AreaDto;
import org.acme.features.market.area.application.service.AreasVisibilityService;
import org.acme.features.market.area.domain.Areas;
import org.acme.features.market.area.domain.gateway.AreaWriteRepositoryGateway;
import org.acme.features.market.area.domain.model.Area;
import org.acme.features.market.area.domain.model.AreaRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateAreaUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Areas aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final AreaWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<AreaUpdateAllow> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final AreasVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public AreaUpdateAllow allow(final Interaction query, final AreaRef reference) {
    AreaUpdateAllow base =
        AreaUpdateAllow.build(query, Optional.of(reference), true, "Allowed by default");
    updateAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public AreaUpdateAllow allow(final Interaction query) {
    AreaUpdateAllow base =
        AreaUpdateAllow.build(query, Optional.empty(), true, "Allowed by default");
    updateAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @return
   */
  public AreaUpdateAllow allow() {
    return null;
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public AreaUpdateResult update(final AreaUpdateCommand command) {
    CompletionStage<Optional<Area>> updated =
        allow(command, command.getReference()).getDetail().thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.copyWithFixed(command, command.getDto())
              .thenCompose(builder -> visibility
                  .retrieveVisible(command, command.getReference().getUidValue())
                  .thenCompose(op -> saveIfIsPresent(op, builder)));
        });
    return AreaUpdateResult.builder().command(command)
        .area(updated.thenCompose(entity -> mapEntity(command, entity))).build();
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param oparea
   * @return The slide with some values
   */
  private CompletionStage<Optional<AreaDto>> mapEntity(final AreaUpdateCommand command,
      final Optional<Area> oparea) {
    return oparea.map(area -> visibility.copyWithHidden(command, area).thenApply(Optional::of))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param original
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Area>> saveEntity(final Area original, final AreaDto dto) {
    return aggregate.modify(original, dto.toEntityBuilder(Optional.of(original)))
        .thenCompose(area -> gateway.update(original, area).thenApply(Optional::of));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param result
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Area>> saveIfIsPresent(final Optional<Area> result,
      final AreaDto dto) {
    return result.map(original -> saveEntity(original, dto))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
