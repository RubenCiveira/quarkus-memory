package org.acme.features.market.place.application.usecase;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.place.application.allow.PlaceUpdateAllow;
import org.acme.features.market.place.application.interaction.PlaceDto;
import org.acme.features.market.place.application.interaction.command.PlaceUpdateCommand;
import org.acme.features.market.place.application.interaction.query.PlaceEntityAllowQuery;
import org.acme.features.market.place.application.interaction.result.PlaceUpdateResult;
import org.acme.features.market.place.application.usecase.service.PlacesVisibilityService;
import org.acme.features.market.place.domain.Places;
import org.acme.features.market.place.domain.gateway.PlaceWriteRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdatePlaceUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Places aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final PlaceWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<PlaceUpdateAllow> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public PlaceUpdateAllow allow(final PlaceEntityAllowQuery query) {
    PlaceUpdateAllow base =
        PlaceUpdateAllow.build(query.getReference(), true, "Allowed by default");
    updateAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @return
   */
  public PlaceUpdateAllow allow() {
    return null;
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public PlaceUpdateResult update(final PlaceUpdateCommand command) {
    CompletionStage<Optional<Place>> updated = allow(command).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.copyWithFixed(command, command.getDto()).thenCompose(
          builder -> visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(op -> saveIfIsPresent(op, builder)));
    });
    return PlaceUpdateResult.builder().command(command)
        .place(updated.thenCompose(entity -> mapEntity(command, entity))).build();
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  private PlaceUpdateAllow allow(final PlaceUpdateCommand query) {
    return allow(PlaceEntityAllowQuery.builder().reference(query.getReference()).build(query));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opplace
   * @return The slide with some values
   */
  private CompletionStage<Optional<PlaceDto>> mapEntity(final PlaceUpdateCommand command,
      final Optional<Place> opplace) {
    return opplace.map(place -> visibility.copyWithHidden(command, place).thenApply(Optional::of))
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
  private CompletionStage<Optional<Place>> saveEntity(final Place original, final PlaceDto dto) {
    return aggregate.modify(original, dto.toEntityBuilder(Optional.of(original)))
        .thenCompose(place -> gateway.update(original, place).thenApply(Optional::of));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param result
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Place>> saveIfIsPresent(final Optional<Place> result,
      final PlaceDto dto) {
    return result.map(original -> saveEntity(original, dto))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
