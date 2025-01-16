package org.acme.features.market.place.domain.gateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.place.domain.model.Place;

public interface PlaceCacheGateway {

  /**
   * @autogenerated CacheGatewayGenerator
   * @param place
   * @return
   */
  public CompletionStage<Void> remove(Place place);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<PlaceCached>> retrieve(PlaceFilter filter, PlaceCursor cursor);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @param places
   * @return
   */
  public CompletionStage<Void> store(PlaceFilter filter, PlaceCursor cursor, List<Place> places);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param place
   * @return
   */
  public CompletionStage<Void> update(Place place);
}