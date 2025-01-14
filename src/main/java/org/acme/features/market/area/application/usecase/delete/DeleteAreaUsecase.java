package org.acme.features.market.area.application.usecase.delete;

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
public class DeleteAreaUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Areas aggregate;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<AreaDeleteAllow> deleteAllow;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final AreaWriteRepositoryGateway gateway;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final AreasVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public AreaDeleteAllow allow(final Interaction query, final AreaRef reference) {
    AreaDeleteAllow base =
        AreaDeleteAllow.build(query, Optional.of(reference), true, "Allowed by default");
    deleteAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public AreaDeleteAllow allow(final Interaction query) {
    AreaDeleteAllow base =
        AreaDeleteAllow.build(query, Optional.empty(), true, "Allowed by default");
    deleteAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public AreaDeleteResult delete(final AreaDeleteCommand command) {
    CompletionStage<Optional<Area>> updated =
        allow(command, command.getReference()).getDetail().thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(this::deleteIfIsPresent);
        });
    return AreaDeleteResult.builder().command(command)
        .area(updated.thenCompose(entity -> mapEntity(command, entity))).build();
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Area>> deleteEntity(final Area original) {
    return aggregate.clean(original)
        .thenCompose(area -> gateway.delete(area).thenApply(Optional::of));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Area>> deleteIfIsPresent(final Optional<Area> result) {
    return result.map(this::deleteEntity)
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param oparea
   * @return The slide with some values
   */
  private CompletionStage<Optional<AreaDto>> mapEntity(final AreaDeleteCommand command,
      final Optional<Area> oparea) {
    return oparea.map(area -> visibility.copyWithHidden(command, area).thenApply(Optional::of))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
