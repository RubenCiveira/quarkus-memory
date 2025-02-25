package org.acme.features.market.merchant.infrastructure.driven;

import java.util.Optional;
import java.util.function.Function;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;
import org.acme.features.market.merchant.infrastructure.repository.MerchantRepository;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MerchantWriteGatewayAdapter implements MerchantWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final MerchantRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @return
   */
  @Override
  public long count(MerchantFilter filter) {
    return repository.count(filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public Merchant create(Merchant entity) {
    return repository.create(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public Merchant create(Merchant entity, Function<Merchant, Boolean> verifier) {
    return repository.create(entity, verifier);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public Merchant delete(Merchant entity) {
    return repository.delete(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public Merchant enrich(MerchantRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public boolean exists(String uid, Optional<MerchantFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slider<Merchant> list(MerchantFilter filter, MerchantCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public Optional<Merchant> retrieve(String uid, Optional<MerchantFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @param entity
   * @return
   */
  @Override
  public Merchant update(MerchantRef reference, Merchant entity) {
    return repository.update(entity);
  }
}
