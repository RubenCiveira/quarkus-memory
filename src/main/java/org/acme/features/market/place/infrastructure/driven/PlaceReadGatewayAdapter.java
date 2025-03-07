package org.acme.features.market.place.infrastructure.driven;

import java.util.Optional;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlaceReadRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;
import org.acme.features.market.place.infrastructure.repository.PlaceRepository;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class PlaceReadGatewayAdapter implements PlaceReadRepositoryGateway {

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   */
  private final PlaceRepository repository;

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @return
   */
  @Override
  public long count(PlaceFilter filter) {
    return repository.count(filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public Place enrich(PlaceRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public boolean exists(String uid, Optional<PlaceFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slider<Place> list(PlaceFilter filter, PlaceCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public Optional<Place> retrieve(String uid, Optional<PlaceFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
