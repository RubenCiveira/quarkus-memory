package org.acme.features.market.place.application.usecase;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.exception.NotAllowedException;
import org.acme.common.store.BinaryContent;
import org.acme.features.market.place.application.allow.PlaceRetrieveAllow;
import org.acme.features.market.place.application.interaction.query.PlaceEntityAllowQuery;
import org.acme.features.market.place.application.interaction.query.PlaceRetrieveUploadPhotoQuery;
import org.acme.features.market.place.application.interaction.result.PlaceRetrieveUploadPhotoResult;
import org.acme.features.market.place.application.usecase.service.PlacesVisibilityService;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlacePhotoUploadGateway;
import org.acme.features.market.place.domain.gateway.PlaceReadRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrievePhotoUploadUsecase {

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final PlaceReadRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<PlaceRetrieveAllow> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final PlacePhotoUploadGateway store;

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
   * @param query
   * @return The slide with some values
   */
  public PlaceRetrieveUploadPhotoResult read(final PlaceRetrieveUploadPhotoQuery query) {
    CompletionStage<Optional<BinaryContent>> result =
        allow(query).getDetail().thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          PlaceFilter filter =
              PlaceFilter.builder().uid(query.getReference().getUidValue()).build();
          return visibility.visibleFilter(query, filter).thenCompose(visibleFilter -> gateway
              .retrieve(query.getReference().getUidValue(), Optional.of(visibleFilter)));
        }).thenCompose(this::readPhotoIfIsPresent);
    return PlaceRetrieveUploadPhotoResult.builder().interaction(query).photo(result).build();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  private PlaceRetrieveAllow allow(final PlaceRetrieveUploadPhotoQuery query) {
    return allow(PlaceEntityAllowQuery.builder().reference(query.getReference()).build(query));
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param optionalEntity
   * @return
   */
  private CompletionStage<Optional<BinaryContent>> readPhotoIfIsPresent(
      final Optional<Place> optionalEntity) {
    return optionalEntity.map(entity -> store.readPhoto(entity))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
