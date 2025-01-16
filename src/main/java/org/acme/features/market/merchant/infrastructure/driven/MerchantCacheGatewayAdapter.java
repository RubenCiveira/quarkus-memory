package org.acme.features.market.merchant.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantCached;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.model.Merchant;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@RequiredArgsConstructor
@Slf4j
public class MerchantCacheGatewayAdapter implements MerchantCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("merchant")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param merchant
   * @return
   */
  public CompletionStage<Void> remove(final Merchant merchant) {
    log.trace("Invalidating merchant cache due to the removal of {}", merchant);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<MerchantCached>> retrieve(final MerchantFilter filter,
      final MerchantCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at merchant cache for the key {}", key);
    return cache.<String, MerchantCached>get(key, k -> null).subscribeAsCompletionStage()
        .thenCompose(cached -> cached == null ? notInCache(key) : existingValue(key, cached));
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param merchants
   * @return
   */
  public CompletionStage<Void> store(final MerchantFilter filter, final MerchantCursor cursor,
      final List<Merchant> merchants) {
    String key = key(filter, cursor);
    return cache
        .<String, MerchantCached>get(key,
            k -> MerchantCached.builder().since(OffsetDateTime.now()).value(merchants).build())
        .subscribeAsCompletionStage().<Void>thenApply(chitem -> null);
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param merchant
   * @return
   */
  public CompletionStage<Void> update(final Merchant merchant) {
    log.trace("Invalidating merchant cache due to the update of {}", merchant);
    return invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @param cached
   * @return
   */
  private CompletionStage<Optional<MerchantCached>> existingValue(final String key,
      final MerchantCached cached) {
    log.trace("There is value for key {} in the cache merchant: {}", key, cached);
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
  private String key(final MerchantFilter filter, final MerchantCursor cursor) {
    return filter.toString() + "-" + cursor.toString();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param key
   * @return
   */
  private CompletableFuture<Optional<MerchantCached>> notInCache(final String key) {
    log.trace("There is no value for key {} on the cache merchant", key);
    return cache.invalidateIf(cachekey -> cachekey.equals(key)).subscribeAsCompletionStage()
        .thenApply(_inv -> Optional.empty());
  }
}