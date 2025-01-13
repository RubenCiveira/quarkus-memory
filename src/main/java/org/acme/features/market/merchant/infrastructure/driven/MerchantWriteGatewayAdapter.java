package org.acme.features.market.merchant.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;
import org.acme.features.market.merchant.infrastructure.repository.MerchantRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Named;

@RequestScoped
public class MerchantWriteGatewayAdapter implements MerchantWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final MerchantRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param readSource
   * @param writeSource
   */
  public MerchantWriteGatewayAdapter(final DataSource readSource,
      @Named("write-channel") final Instance<DataSource> writeSource) {
    this.repository =
        new MerchantRepository(writeSource.isResolvable() ? writeSource.get() : readSource);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Optional<Merchant>> create(Merchant entity) {
    return repository.create(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public CompletionStage<Optional<Merchant>> create(Merchant entity,
      Function<Merchant, CompletionStage<Boolean>> verifier) {
    return repository.create(entity, verifier);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Merchant> delete(Merchant entity) {
    return repository.delete(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Merchant> enrich(MerchantRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<MerchantFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slide<Merchant> list(MerchantFilter filter, MerchantCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Merchant>> retrieve(String uid, Optional<MerchantFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Merchant> update(MerchantRef reference, Merchant entity) {
    return repository.update(entity);
  }
}
