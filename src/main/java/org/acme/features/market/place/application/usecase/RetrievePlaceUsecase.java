package org.acme.features.market.place.application.usecase;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.place.application.allow.PlaceRetrieveAllow;
import org.acme.features.market.place.application.interaction.PlaceDto;
import org.acme.features.market.place.application.interaction.query.PlaceEntityAllowQuery;
import org.acme.features.market.place.application.interaction.query.PlaceRetrieveQuery;
import org.acme.features.market.place.application.interaction.result.PlaceRetrieveResult;
import org.acme.features.market.place.application.usecase.service.PlacesVisibilityService;
import org.acme.features.market.place.domain.model.Place;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrievePlaceUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<PlaceRetrieveAllow> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public PlaceRetrieveAllow allow(final PlaceEntityAllowQuery query) {
    PlaceRetrieveAllow base =
        PlaceRetrieveAllow.build(query.getReference(), true, "Allowed by default");
    retrieveAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public PlaceRetrieveResult retrieve(final PlaceRetrieveQuery query) {
    CompletionStage<Optional<Place>> result = allow(query).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.retrieveVisible(query, query.getReference().getUidValue());
    });
    return PlaceRetrieveResult.builder().interaction(query)
        .place(result.thenCompose(op -> this.mapEntity(query, op))).build();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  private PlaceRetrieveAllow allow(final PlaceRetrieveQuery query) {
    return allow(PlaceEntityAllowQuery.builder().reference(query.getReference()).build(query));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param opplace
   * @return The slide with some values
   */
  private CompletionStage<Optional<PlaceDto>> mapEntity(final PlaceRetrieveQuery query,
      final Optional<Place> opplace) {
    return opplace.map(place -> visibility.copyWithHidden(query, place).thenApply(Optional::of))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
