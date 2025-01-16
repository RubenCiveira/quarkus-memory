package org.acme.features.market.area.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.area.domain.gateway.AreaCacheGateway;
import org.acme.features.market.area.domain.gateway.AreaCached;
import org.acme.features.market.area.domain.gateway.AreaCursor;
import org.acme.features.market.area.domain.gateway.AreaFilter;
import org.acme.features.market.area.domain.model.Area;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
@RequiredArgsConstructor
public class AreaCacheGatewayAdapter implements AreaCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("area")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param area
   * @return
   */
  public CompletionStage<Void> remove(final Area area) {
    log.trace("Invalidating area cache due to the removal of {}", area);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<AreaCached>> retrieve(final AreaFilter filter,
      final AreaCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at area cache for the key {}", key);
    return cache.<String, AreaCached>get(key, k -> null).subscribeAsCompletionStage()
        .thenCompose(cached -> cached == null ? notInCache(key) : existingValue(key, cached));
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param areas
   * @return
   */
  public CompletionStage<Void> store(final AreaFilter filter, final AreaCursor cursor,
      final List<Area> areas) {
    String key = key(filter, cursor);
    return cache
        .<String, AreaCached>get(key,
            k -> AreaCached.builder().since(OffsetDateTime.now()).value(areas).build())
        .subscribeAsCompletionStage().<Void>thenApply(chitem -> null);
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param area
   * @return
   */
  public CompletionStage<Void> update(final Area area) {
    log.trace("Invalidating area cache due to the update of {}", area);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @param cached
   * @return
   */
  private CompletionStage<Optional<AreaCached>> existingValue(final String key,
      final AreaCached cached) {
    log.trace("There is value for key {} in the cache area: {}", key, cached);
    return CompletableFuture.completedStage(Optional.of(cached));
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @return
   */
  private CompletableFuture<Void> invalidate() {
    return cache.invalidateAll().subscribeAsCompletionStage();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  private String key(final AreaFilter filter, final AreaCursor cursor) {
    return filter.toString() + "-" + cursor.toString();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @return
   */
  private CompletableFuture<Optional<AreaCached>> notInCache(final String key) {
    log.trace("There is no value for key {} on the cache area", key);
    return cache.invalidateIf(cachekey -> cachekey.equals(key)).subscribeAsCompletionStage()
        .thenApply(_inv -> Optional.empty());
  }
}
