package org.acme.features.market.place.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlaceWriteRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;
import org.acme.features.market.place.infrastructure.repository.PlaceRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Named;

@RequestScoped
public class PlaceWriteGatewayAdapter implements PlaceWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final PlacePhotoUploadGatewayAdapter placePhotoUploadGatewayAdapter;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final PlaceRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param readSource
   * @param writeSource
   * @param placePhotoUploadGatewayAdapter
   */
  public PlaceWriteGatewayAdapter(final DataSource readSource,
      @Named("write-channel") final Instance<DataSource> writeSource,
      PlacePhotoUploadGatewayAdapter placePhotoUploadGatewayAdapter) {
    this.repository = new PlaceRepository(readSource);
    this.placePhotoUploadGatewayAdapter = placePhotoUploadGatewayAdapter;
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Optional<Place>> create(Place entity) {
    return placePhotoUploadGatewayAdapter.commitPhoto(entity, Optional.empty())
        .thenCompose(_res -> repository.create(entity));
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public CompletionStage<Optional<Place>> create(Place entity,
      Function<Place, CompletionStage<Boolean>> verifier) {
    return placePhotoUploadGatewayAdapter.commitPhoto(entity, Optional.empty())
        .thenCompose(_res -> repository.create(entity, verifier));
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Place> delete(Place entity) {
    return repository.delete(entity).thenCompose(
        result -> placePhotoUploadGatewayAdapter.deletePhoto(entity).thenApply(_res -> result));
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Place> enrich(PlaceRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<PlaceFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public CompletionStage<Slide<Place>> list(PlaceFilter filter, PlaceCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Place>> retrieve(String uid, Optional<PlaceFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Place> update(PlaceRef reference, Place entity) {
    return enrich(reference)
        .thenCompose(
            stored -> placePhotoUploadGatewayAdapter.commitPhoto(entity, Optional.of(stored)))
        .thenCompose(_res -> repository.update(entity));
  }
}
