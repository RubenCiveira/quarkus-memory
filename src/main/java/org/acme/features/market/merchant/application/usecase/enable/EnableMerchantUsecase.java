package org.acme.features.market.merchant.application.usecase.enable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.application.usecase.enable.event.MerchantEnableAllowPipelineStageEvent;
import org.acme.features.market.merchant.domain.Merchants;
import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class EnableMerchantUsecase {

  /**
   * @autogenerated ActionUsecaseGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated ActionUsecaseGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ActionUsecaseGenerator
   */
  private final Event<MerchantEnableAllowPipelineStageEvent> enableAllow;

  /**
   * @autogenerated ActionUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * @autogenerated ActionUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated ActionUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final MerchantRef reference) {
    MerchantEnableAllowPipelineStageEvent base = MerchantEnableAllowPipelineStageEvent.build(query,
        Optional.of(reference), true, "Allowed by default");
    enableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ActionUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    MerchantEnableAllowPipelineStageEvent base = MerchantEnableAllowPipelineStageEvent.build(query,
        Optional.empty(), true, "Allowed by default");
    enableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ActionUsecaseGenerator
   * @return
   */
  public MerchantEnableAllowPipelineStageEvent allow() {
    return null;
  }

  /**
   * The slide with some values
   *
   * @autogenerated ActionUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<MerchantEnableResult> enable(final MerchantEnableCommand command) {
    CompletionStage<Optional<Merchant>> updated =
        allow(command, command.getReference()).thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(op -> enableIfIsPresent(op));
        });
    return updated.thenCompose(entity -> mapEntity(command, entity));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ActionUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> enableEntity(final Merchant original) {
    return aggregate.enable(original).thenCompose(merchant -> gateway.update(original, merchant))
        .thenCompose(updated -> cache.update(updated).thenApply(_ready -> Optional.of(updated)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ActionUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> enableIfIsPresent(final Optional<Merchant> result) {
    return result.map(original -> enableEntity(original))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ActionUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opmerchant
   * @return The slide with some values
   */
  private CompletionStage<MerchantEnableResult> mapEntity(final MerchantEnableCommand command,
      final Optional<Merchant> opmerchant) {
    return opmerchant
        .map(merchant -> visibility.copyWithHidden(command, merchant)
            .thenApply(visible -> MerchantEnableResult.builder().command(command)
                .merchant(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            MerchantEnableResult.builder().command(command).merchant(Optional.empty()).build()));
  }
}
