package org.acme.features.market.merchant.application.usecase.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.application.usecase.create.event.MerchantCreateAllowPipelineStageEvent;
import org.acme.features.market.merchant.domain.Merchants;
import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
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
  private final Event<MerchantCreateAllowPipelineStageEvent> createAllow;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Tracer tracer;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    Span startSpan = tracer.spanBuilder("merchant-create-allow").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      MerchantCreateAllowPipelineStageEvent base =
          MerchantCreateAllowPipelineStageEvent.build(query, true, "Allowed by default");
      createAllow.fire(base);
      return base.getDetail().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setAttribute("allowed", val.isAllowed());
          startSpan.setAttribute("reason", val.getDescription());
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<MerchantCreateResult> create(final MerchantCreateCommand query) {
    Span startSpan = tracer.spanBuilder("merchant-create").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Optional<Merchant>> create = allow(query).thenCompose(detail -> {
        if (!detail.isAllowed()) {
          throw new NotAllowedException(detail.getDescription());
        }
        return visibility.copyWithFixed(query, query.getDto())
            .thenCompose(builder -> aggregate.initialize(builder.toEntityBuilder(Optional.empty()))
                .thenCompose(merchantEntity -> createAndVerify(query, merchantEntity)));
      });
      return create.thenCompose(merchant -> mapEntity(query, merchant)).whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @param merchantEntity
   * @return
   */
  private CompletionStage<Optional<Merchant>> createAndVerify(final MerchantCreateCommand query,
      final Merchant merchantEntity) {
    Span startSpan = tracer.spanBuilder("merchant-run-create-and-verify").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return gateway
          .create(merchantEntity,
              created -> visibility.checkVisibility(query, created.getUidValue()))
          .thenCompose(validated -> validated
              .map(created -> cache.update(created).thenApply(_ready -> Optional.of(created)))
              .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
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
    Span startSpan = tracer.spanBuilder("merchant-map-created-entity").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return opmerchant
          .map(merchant -> visibility.copyWithHidden(command, merchant)
              .thenApply(visible -> MerchantCreateResult.builder().command(command)
                  .merchant(Optional.of(visible)).build()))
          .orElseGet(() -> CompletableFuture.completedStage(
              MerchantCreateResult.builder().command(command).merchant(Optional.empty()).build()))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }
}
