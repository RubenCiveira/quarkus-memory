package org.acme.features.market.medal.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.gateway.MedalWriteRepositoryGateway;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;
import org.acme.features.market.medal.infrastructure.repository.MedalRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Named;

@RequestScoped
public class MedalWriteGatewayAdapter implements MedalWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final MedalRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param readSource
   * @param writeSource
   */
  public MedalWriteGatewayAdapter(final DataSource readSource,
      @Named("write-channel") final Instance<DataSource> writeSource) {
    this.repository = new MedalRepository(readSource);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Optional<Medal>> create(Medal entity) {
    return repository.create(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public CompletionStage<Optional<Medal>> create(Medal entity,
      Function<Medal, CompletionStage<Boolean>> verifier) {
    return repository.create(entity, verifier);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Medal> delete(Medal entity) {
    return repository.delete(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Medal> enrich(MedalRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<MedalFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public CompletionStage<Slide<Medal>> list(MedalFilter filter, MedalCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Medal>> retrieve(String uid, Optional<MedalFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param reference
   * @param entity
   * @return
   */
  @Override
  public CompletionStage<Medal> update(MedalRef reference, Medal entity) {
    return repository.update(entity);
  }
}
