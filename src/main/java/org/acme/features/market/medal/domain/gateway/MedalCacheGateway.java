package org.acme.features.market.medal.domain.gateway;

import java.util.List;
import java.util.Optional;

import org.acme.features.market.medal.domain.model.Medal;

public interface MedalCacheGateway {

  /**
   * @autogenerated CacheGatewayGenerator
   * @param medal
   */
  public void remove(Medal medal);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Optional<MedalCached> retrieve(MedalFilter filter, MedalCursor cursor);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @param medals
   * @return
   */
  public MedalCached store(MedalFilter filter, MedalCursor cursor, List<Medal> medals);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param medal
   */
  public void update(Medal medal);
}
