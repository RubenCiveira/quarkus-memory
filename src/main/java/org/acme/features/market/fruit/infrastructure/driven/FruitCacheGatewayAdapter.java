package org.acme.features.market.fruit.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitCached;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@RequiredArgsConstructor
@Slf4j
public class FruitCacheGatewayAdapter implements FruitCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("fruit")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param fruit
   * @return
   */
  public CompletionStage<Void> remove(final Fruit fruit) {
    log.trace("Invalidating fruit cache due to the removal of {}", fruit);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<FruitCached>> retrieve(final FruitFilter filter,
      final FruitCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at fruit cache for the key {}", key);
    return cache.<String, FruitCached>get(key, k -> null).subscribeAsCompletionStage()
        .thenCompose(cached -> cached == null ? notInCache(key) : existingValue(key, cached));
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param fruits
   * @return
   */
  public CompletionStage<Void> store(final FruitFilter filter, final FruitCursor cursor,
      final List<Fruit> fruits) {
    String key = key(filter, cursor);
    return cache
        .<String, FruitCached>get(key,
            k -> FruitCached.builder().since(OffsetDateTime.now()).value(fruits).build())
        .subscribeAsCompletionStage().<Void>thenApply(chitem -> null);
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param fruit
   * @return
   */
  public CompletionStage<Void> update(final Fruit fruit) {
    log.trace("Invalidating fruit cache due to the update of {}", fruit);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @param cached
   * @return
   */
  private CompletionStage<Optional<FruitCached>> existingValue(final String key,
      final FruitCached cached) {
    log.trace("There is value for key {} in the cache fruit: {}", key, cached);
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
  private String key(final FruitFilter filter, final FruitCursor cursor) {
    return filter.toString() + "-" + cursor.toString();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @return
   */
  private CompletableFuture<Optional<FruitCached>> notInCache(final String key) {
    log.trace("There is no value for key {} on the cache fruit", key);
    return cache.invalidateIf(cachekey -> cachekey.equals(key)).subscribeAsCompletionStage()
        .thenApply(_inv -> Optional.empty());
  }
}
