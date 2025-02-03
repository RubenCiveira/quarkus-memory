package org.acme.features.market.medal.infrastructure.driven;

import java.util.Optional;

import javax.sql.DataSource;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.gateway.MedalReadRepositoryGateway;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;
import org.acme.features.market.medal.infrastructure.repository.MedalRepository;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MedalReadGatewayAdapter implements MedalReadRepositoryGateway {

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   */
  private final MedalRepository repository;

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param readSource
   */
  public MedalReadGatewayAdapter(final DataSource readSource) {
    this.repository = new MedalRepository(readSource);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public Medal enrich(MedalRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public boolean exists(String uid, Optional<MedalFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slider<Medal> list(MedalFilter filter, MedalCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public Optional<Medal> retrieve(String uid, Optional<MedalFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
