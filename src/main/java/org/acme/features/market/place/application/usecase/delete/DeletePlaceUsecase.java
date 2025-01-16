package org.acme.features.market.place.application.usecase.delete;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.place.application.service.PlacesVisibilityService;
import org.acme.features.market.place.application.usecase.delete.event.PlaceDeleteAllowPipelineStageEvent;
import org.acme.features.market.place.domain.Places;
import org.acme.features.market.place.domain.gateway.PlaceCacheGateway;
import org.acme.features.market.place.domain.gateway.PlaceWriteRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeletePlaceUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Places aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final PlaceCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<PlaceDeleteAllowPipelineStageEvent> deleteAllow;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final PlaceWriteRepositoryGateway gateway;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final PlaceRef reference) {
    PlaceDeleteAllowPipelineStageEvent base = PlaceDeleteAllowPipelineStageEvent.build(query,
        Optional.of(reference), true, "Allowed by default");
    deleteAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    PlaceDeleteAllowPipelineStageEvent base = PlaceDeleteAllowPipelineStageEvent.build(query,
        Optional.empty(), true, "Allowed by default");
    deleteAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<PlaceDeleteResult> delete(final PlaceDeleteCommand command) {
    CompletionStage<Optional<Place>> updated =
        allow(command, command.getReference()).thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(this::deleteIfIsPresent);
        });
    return updated.thenCompose(entity -> mapEntity(command, entity));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Place>> deleteEntity(final Place original) {
    return aggregate.clean(original).thenCompose(place -> gateway.delete(place))
        .thenCompose(deleted -> cache.remove(deleted).thenApply(_ready -> Optional.of(deleted)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Place>> deleteIfIsPresent(final Optional<Place> result) {
    return result.map(this::deleteEntity)
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opplace
   * @return The slide with some values
   */
  private CompletionStage<PlaceDeleteResult> mapEntity(final PlaceDeleteCommand command,
      final Optional<Place> opplace) {
    return opplace
        .map(place -> visibility.copyWithHidden(command, place)
            .thenApply(visible -> PlaceDeleteResult.builder().command(command)
                .place(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            PlaceDeleteResult.builder().command(command).place(Optional.empty()).build()));
  }
}
