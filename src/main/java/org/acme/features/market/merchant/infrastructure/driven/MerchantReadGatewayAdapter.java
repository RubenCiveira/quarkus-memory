package org.acme.features.market.merchant.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantReadRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;
import org.acme.features.market.merchant.infrastructure.repository.MerchantRepository;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MerchantReadGatewayAdapter implements MerchantReadRepositoryGateway {

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   */
  private final MerchantRepository repository;

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param readSource
   */
  public MerchantReadGatewayAdapter(final DataSource readSource) {
    this.repository = new MerchantRepository(readSource);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param reference
   * @return
   */
  @Override
  public CompletionStage<Merchant> enrich(MerchantRef reference) {
    return repository.enrich(reference);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Boolean> exists(String uid, Optional<MerchantFilter> filter) {
    return repository.exists(uid, filter);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  @Override
  public Slide<Merchant> list(MerchantFilter filter, MerchantCursor cursor) {
    return repository.list(filter, cursor);
  }

  /**
   * @autogenerated ReadAdaterGatewayGenerator
   * @param uid
   * @param filter
   * @return
   */
  @Override
  public CompletionStage<Optional<Merchant>> retrieve(String uid, Optional<MerchantFilter> filter) {
    return repository.retrieve(uid, filter);
  }
}
