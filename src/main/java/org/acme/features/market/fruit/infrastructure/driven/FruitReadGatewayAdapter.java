package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;

import javax.sql.DataSource;

import org.acme.common.algorithms.Slider;
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
   * @param filter
   * @return
   */
  @Override
  public long count(FruitFilter filter) {
    return repository.count(filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public Fruit enrich(FruitRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public boolean exists(String uid, Optional<FruitFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slider<Fruit> list(FruitFilter filter, FruitCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public Optional<Fruit> retrieve(String uid, Optional<FruitFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
