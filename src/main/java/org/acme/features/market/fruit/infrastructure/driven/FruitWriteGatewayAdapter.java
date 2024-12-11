package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitWriteRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.infrastructure.repository.FruitRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class FruitWriteGatewayAdapter implements FruitWriteRepositoryGateway {

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   */
  private final FruitRepository repository;

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param readSource
   * @param writeSource
   */
  public FruitWriteGatewayAdapter(final DataSource readSource,
      @Named("write-channel") final Instance<DataSource> writeSource) {
    this.repository =
        new FruitRepository(writeSource.isResolvable() ? writeSource.get() : readSource);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletableFuture<Optional<Fruit>> create(Fruit entity) {
    return repository.create(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @param verifier
   * @return
   */
  @Override
  public CompletableFuture<Optional<Fruit>> create(Fruit entity,
      Function<Fruit, CompletableFuture<Boolean>> verifier) {
    return repository.create(entity, verifier);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletableFuture<Fruit> delete(Fruit entity) {
    return repository.delete(entity);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletableFuture<Boolean> exists(String uid, Optional<FruitFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slide<Fruit> list(FruitFilter filter, FruitCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletableFuture<Optional<Fruit>> retrieve(String uid, Optional<FruitFilter> filter) {
    return repository.retrieve(uid, filter);
  }

  /**
   * @autogenerated WriteAdaterGatewayGenerator
   * @param entity
   * @return
   */
  @Override
  public CompletableFuture<Fruit> update(Fruit entity) {
    return repository.update(entity);
  }
}
