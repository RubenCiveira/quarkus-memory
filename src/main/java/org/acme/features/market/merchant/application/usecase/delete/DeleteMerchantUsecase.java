package org.acme.features.market.merchant.application.usecase.delete;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.application.usecase.delete.event.MerchantDeleteAllowPipelineStageEvent;
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
public class DeleteMerchantUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<MerchantDeleteAllowPipelineStageEvent> deleteAllow;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final MerchantRef reference) {
    MerchantDeleteAllowPipelineStageEvent base = MerchantDeleteAllowPipelineStageEvent.build(query,
        Optional.of(reference), true, "Allowed by default");
    deleteAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    MerchantDeleteAllowPipelineStageEvent base = MerchantDeleteAllowPipelineStageEvent.build(query,
        Optional.empty(), true, "Allowed by default");
    deleteAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<MerchantDeleteResult> delete(final MerchantDeleteCommand command) {
    CompletionStage<Optional<Merchant>> updated =
        allow(command, command.getReference()).thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(this::deleteIfIsPresent);
        });
    return updated.thenCompose(entity -> mapEntity(command, entity));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> deleteEntity(final Merchant original) {
    return aggregate.clean(original).thenCompose(merchant -> gateway.delete(merchant))
        .thenCompose(deleted -> cache.remove(deleted).thenApply(_ready -> Optional.of(deleted)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Merchant>> deleteIfIsPresent(final Optional<Merchant> result) {
    return result.map(this::deleteEntity)
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opmerchant
   * @return The slide with some values
   */
  private CompletionStage<MerchantDeleteResult> mapEntity(final MerchantDeleteCommand command,
      final Optional<Merchant> opmerchant) {
    return opmerchant
        .map(merchant -> visibility.copyWithHidden(command, merchant)
            .thenApply(visible -> MerchantDeleteResult.builder().command(command)
                .merchant(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            MerchantDeleteResult.builder().command(command).merchant(Optional.empty()).build()));
  }
}