package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitReadRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;
import org.acme.features.market.fruit.infrastructure.repository.FruitRepository;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class FruitReadGatewayAdapter implements FruitReadRepositoryGateway {

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   */
  private final FruitRepository repository;

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param readSource
   */
  public FruitReadGatewayAdapter(final DataSource readSource) {
    this.repository = new FruitRepository(readSource);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Fruit> enrich(FruitRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<FruitFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slide<Fruit> list(FruitFilter filter, FruitCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Fruit>> retrieve(String uid, Optional<FruitFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
