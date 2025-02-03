package org.acme.features.market.merchant.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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
@Slf4j
@RequiredArgsConstructor
public class MerchantCacheGatewayAdapter implements MerchantCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("merchant")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param merchant
   */
  @Override
  public void remove(final Merchant merchant) {
    log.trace("Invalidating merchant cache due to the removal of {}", merchant);
    invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Optional<MerchantCached> retrieve(final MerchantFilter filter,
      final MerchantCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at merchant cache for the key {}", key);
    return cache.<String, MerchantCached>get(key, k -> null)
        .map(cached -> Optional.ofNullable(cached)).await().indefinitely();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param merchants
   * @return
   */
  public MerchantCached store(final MerchantFilter filter, final MerchantCursor cursor,
      final List<Merchant> merchants) {
    String key = key(filter, cursor);
    cache.invalidate(key).await().indefinitely();
    return cache
        .<String, MerchantCached>get(key,
            k -> MerchantCached.builder().since(OffsetDateTime.now()).value(merchants).build())
        .await().indefinitely();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param merchant
   */
  @Override
  public void update(final Merchant merchant) {
    log.trace("Invalidating merchant cache due to the update of {}", merchant);
    invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  private void invalidate() {
    cache.invalidateAll().await().indefinitely();
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
}
