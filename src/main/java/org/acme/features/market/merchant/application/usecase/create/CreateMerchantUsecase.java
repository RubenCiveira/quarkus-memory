package org.acme.features.market.merchant.application.usecase.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.domain.Merchants;
import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreateMerchantUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<MerchantCreateAllow> createAllow;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public MerchantCreateAllow allow(final Interaction query) {
    MerchantCreateAllow base = MerchantCreateAllow.build(query, true, "Allowed by default");
    createAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<MerchantCreateResult> create(final MerchantCreateCommand query) {
    CompletionStage<Optional<Merchant>> create = allow(query).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.copyWithFixed(query, query.getDto())
          .thenCompose(builder -> aggregate.initialize(builder.toEntityBuilder(Optional.empty()))
              .thenCompose(merchantEntity -> createAndVerify(query, merchantEntity)));
    });
    return create.thenCompose(merchant -> mapEntity(query, merchant));
  }

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @param merchantEntity
   * @return
   */
  private CompletionStage<Optional<Merchant>> createAndVerify(final MerchantCreateCommand query,
      final Merchant merchantEntity) {
    return gateway
        .create(merchantEntity, created -> visibility.checkVisibility(query, created.getUidValue()))
        .thenCompose(validated -> validated
            .map(created -> cache.update(created).thenApply(_ready -> Optional.of(created)))
            .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())));
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opmerchant
   * @return The slide with some values
   */
  private CompletionStage<MerchantCreateResult> mapEntity(final MerchantCreateCommand command,
      final Optional<Merchant> opmerchant) {
    return opmerchant
        .map(merchant -> visibility.copyWithHidden(command, merchant)
            .thenApply(visible -> MerchantCreateResult.builder().command(command)
                .merchant(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            MerchantCreateResult.builder().command(command).merchant(Optional.empty()).build()));
  }
}
