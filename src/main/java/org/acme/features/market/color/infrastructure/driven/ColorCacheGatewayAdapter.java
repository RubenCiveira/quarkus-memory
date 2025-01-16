package org.acme.features.market.color.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.color.domain.gateway.ColorCacheGateway;
import org.acme.features.market.color.domain.gateway.ColorCached;
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.model.Color;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@RequiredArgsConstructor
@Slf4j
public class ColorCacheGatewayAdapter implements ColorCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("color")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param color
   * @return
   */
  public CompletionStage<Void> remove(final Color color) {
    log.trace("Invalidating color cache due to the removal of {}", color);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<ColorCached>> retrieve(final ColorFilter filter,
      final ColorCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at color cache for the key {}", key);
    return cache.<String, ColorCached>get(key, k -> null).subscribeAsCompletionStage()
        .thenCompose(cached -> cached == null ? notInCache(key) : existingValue(key, cached));
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param colors
   * @return
   */
  public CompletionStage<Void> store(final ColorFilter filter, final ColorCursor cursor,
      final List<Color> colors) {
    String key = key(filter, cursor);
    return cache
        .<String, ColorCached>get(key,
            k -> ColorCached.builder().since(OffsetDateTime.now()).value(colors).build())
        .subscribeAsCompletionStage().<Void>thenApply(chitem -> null);
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param color
   * @return
   */
  public CompletionStage<Void> update(final Color color) {
    log.trace("Invalidating color cache due to the update of {}", color);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @param cached
   * @return
   */
  private CompletionStage<Optional<ColorCached>> existingValue(final String key,
      final ColorCached cached) {
    log.trace("There is value for key {} in the cache color: {}", key, cached);
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
  private String key(final ColorFilter filter, final ColorCursor cursor) {
    return filter.toString() + "-" + cursor.toString();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @return
   */
  private CompletableFuture<Optional<ColorCached>> notInCache(final String key) {
    log.trace("There is no value for key {} on the cache color", key);
    return cache.invalidateIf(cachekey -> cachekey.equals(key)).subscribeAsCompletionStage()
        .thenApply(_inv -> Optional.empty());
  }
}