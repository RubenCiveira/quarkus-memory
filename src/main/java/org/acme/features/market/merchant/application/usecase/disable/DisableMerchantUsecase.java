package org.acme.features.market.merchant.application.usecase.disable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.application.usecase.disable.event.MerchantDisableAllowPipelineStageEvent;
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
public class DisableMerchantUsecase {

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Event<MerchantDisableAllowPipelineStageEvent> disableAllow;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated ChActionUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final MerchantRef reference) {
    MerchantDisableAllowPipelineStageEvent base = MerchantDisableAllowPipelineStageEvent
        .build(query, Optional.of(reference), true, "Allowed by default");
    disableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ChActionUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    MerchantDisableAllowPipelineStageEvent base = MerchantDisableAllowPipelineStageEvent
        .build(query, Optional.empty(), true, "Allowed by default");
    disableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated ChActionUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<MerchantDisableResult> disable(final MerchantDisableCommand command) {
    CompletionStage<Optional<Merchant>> updated =
        allow(command, command.getReference()).thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(op -> disableIfIsPresent(op));
        });
    return updated.thenCompose(entity -> mapEntity(command, entity));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ChActionUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> disableEntity(final Merchant original) {
    return aggregate.disable(original).thenCompose(merchant -> gateway.update(original, merchant))
        .thenCompose(updated -> cache.update(updated).thenApply(_ready -> Optional.of(updated)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ChActionUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> disableIfIsPresent(final Optional<Merchant> result) {
    return result.map(original -> disableEntity(original))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ChActionUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opmerchant
   * @return The slide with some values
   */
  private CompletionStage<MerchantDisableResult> mapEntity(final MerchantDisableCommand command,
      final Optional<Merchant> opmerchant) {
    return opmerchant
        .map(merchant -> visibility.copyWithHidden(command, merchant)
            .thenApply(visible -> MerchantDisableResult.builder().command(command)
                .merchant(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            MerchantDisableResult.builder().command(command).merchant(Optional.empty()).build()));
  }
}
