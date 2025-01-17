package org.acme.features.market.area.domain.gateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.area.domain.model.Area;

public interface AreaCacheGateway {

  /**
   * @autogenerated CacheGatewayGenerator
   * @param area
   * @return
   */
  public CompletionStage<Void> remove(Area area);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<AreaCached>> retrieve(AreaFilter filter, AreaCursor cursor);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @param areas
   * @return
   */
  public CompletionStage<Void> store(AreaFilter filter, AreaCursor cursor, List<Area> areas);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param area
   * @return
   */
  public CompletionStage<Void> update(Area area);
}
