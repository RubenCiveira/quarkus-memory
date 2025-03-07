package org.acme.features.market.verify.infrastructure.driven;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.acme.features.market.verify.domain.gateway.VerifyCacheGateway;
import org.acme.features.market.verify.domain.gateway.VerifyCached;
import org.acme.features.market.verify.domain.gateway.VerifyCursor;
import org.acme.features.market.verify.domain.gateway.VerifyFilter;
import org.acme.features.market.verify.domain.model.Verify;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Slf4j
@RequiredArgsConstructor
public class VerifyCacheGatewayAdapter implements VerifyCacheGateway {

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @CacheName("verify")
  private final Cache cache;

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   */
  @Override
  public void evict() {
    log.trace("Clearing cache after big operation");
    invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param verify
   */
  @Override
  public void remove(final Verify verify) {
    log.trace("Invalidating verify cache due to the removal of {}", verify);
    invalidate();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Optional<VerifyCached> retrieve(final VerifyFilter filter, final VerifyCursor cursor) {
    String key = key(filter, cursor);
    log.trace("Lookup at verify cache for the key {}", key);
    return cache.<String, VerifyCached>get(key, k -> null)
        .map(cached -> Optional.ofNullable(cached)).await().indefinitely();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @param verifys
   * @return
   */
  public VerifyCached store(final VerifyFilter filter, final VerifyCursor cursor,
      final List<Verify> verifys) {
    String key = key(filter, cursor);
    cache.invalidate(key).await().indefinitely();
    return cache
        .<String, VerifyCached>get(key,
            k -> VerifyCached.builder().since(OffsetDateTime.now()).value(verifys).build())
        .await().indefinitely();
  }

  /**
   * @autogenerated CacheAdaterGatewayGenerator
   * @param verify
   */
  @Override
  public void update(final Verify verify) {
    log.trace("Invalidating verify cache due to the update of {}", verify);
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
  private String key(final VerifyFilter filter, final VerifyCursor cursor) {
    return filter.toString() + "-" + cursor.toString();
  }
}
