package org.acme.features.market.verify.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.verify.domain.gateway.VerifyCursor;
import org.acme.features.market.verify.domain.gateway.VerifyFilter;
import org.acme.features.market.verify.domain.gateway.VerifyWriteRepositoryGateway;
import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.VerifyRef;
import org.acme.features.market.verify.infrastructure.repository.VerifyRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Named;

@RequestScoped
public class VerifyWriteGatewayAdapter implements VerifyWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final VerifyRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param readSource
   * @param writeSource
   */
  public VerifyWriteGatewayAdapter(final DataSource readSource,
      @Named("write-channel") final Instance<DataSource> writeSource) {
    this.repository = new VerifyRepository(readSource);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Optional<Verify>> create(Verify entity) {
    return repository.create(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public CompletionStage<Optional<Verify>> create(Verify entity,
      Function<Verify, CompletionStage<Boolean>> verifier) {
    return repository.create(entity, verifier);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Verify> delete(Verify entity) {
    return repository.delete(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Verify> enrich(VerifyRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<VerifyFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public CompletionStage<Slide<Verify>> list(VerifyFilter filter, VerifyCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Verify>> retrieve(String uid, Optional<VerifyFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Verify> update(VerifyRef reference, Verify entity) {
    return repository.update(entity);
  }
}
