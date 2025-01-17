package org.acme.features.market.color.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.gateway.ColorReadRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;
import org.acme.features.market.color.infrastructure.repository.ColorRepository;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ColorReadGatewayAdapter implements ColorReadRepositoryGateway {

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   */
  private final ColorRepository repository;

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param readSource
   */
  public ColorReadGatewayAdapter(final DataSource readSource) {
    this.repository = new ColorRepository(readSource);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Color> enrich(ColorRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<ColorFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public CompletionStage<Slide<Color>> list(ColorFilter filter, ColorCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Color>> retrieve(String uid, Optional<ColorFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
