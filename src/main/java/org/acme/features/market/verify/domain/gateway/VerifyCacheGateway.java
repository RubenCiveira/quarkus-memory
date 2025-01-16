package org.acme.features.market.verify.domain.gateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.verify.domain.model.Verify;

public interface VerifyCacheGateway {

  /**
   * @autogenerated CacheGatewayGenerator
   * @param verify
   * @return
   */
  public CompletionStage<Void> remove(Verify verify);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Optional<VerifyCached>> retrieve(VerifyFilter filter, VerifyCursor cursor);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param filter
   * @param cursor
   * @param verifys
   * @return
   */
  public CompletionStage<Void> store(VerifyFilter filter, VerifyCursor cursor,
      List<Verify> verifys);

  /**
   * @autogenerated CacheGatewayGenerator
   * @param verify
   * @return
   */
  public CompletionStage<Void> update(Verify verify);
}