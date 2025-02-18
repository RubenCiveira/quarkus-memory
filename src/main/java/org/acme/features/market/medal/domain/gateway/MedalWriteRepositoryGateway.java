package org.acme.features.market.medal.domain.gateway;

import java.util.Optional;
import java.util.function.Function;

import org.acme.common.algorithms.Slider;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;

public interface MedalWriteRepositoryGateway {

  /**
   * The items that would be returned by the query
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param filter a filter to retrieve only matching values
   * @return The items that would be returned by the query
   */
  long count(MedalFilter filter);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @param verifier a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Medal create(Medal entity, Function<Medal, Boolean> verifier);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Medal create(Medal entity);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Medal delete(Medal entity);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param reference a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Medal enrich(MedalRef reference);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  boolean exists(String uid, Optional<MedalFilter> filter);

  /**
   * The slide with some values
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param filter a filter to retrieve only matching values
   * @param cursor a cursor to order and skip
   * @return The slide with some values
   */
  Slider<Medal> list(MedalFilter filter, MedalCursor cursor);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param uid
   * @param filter a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Optional<Medal> retrieve(String uid, Optional<MedalFilter> filter);

  /**
   * Retrieve one single value
   *
   * @autogenerated ReadGatewayRepositoryGenerator
   * @param reference a filter to retrieve only matching values
   * @param entity a filter to retrieve only matching values
   * @return Retrieve one single value
   */
  Medal update(MedalRef reference, Medal entity);
}
