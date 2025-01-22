package org.acme.features.market.verify.application.usecase.retrieve;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.verify.application.service.VerifysVisibilityService;
import org.acme.features.market.verify.application.usecase.retrieve.event.VerifyRetrieveAllowPipelineStageEvent;
import org.acme.features.market.verify.domain.gateway.VerifyCached;
import org.acme.features.market.verify.domain.model.VerifyRef;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrieveVerifyUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<VerifyRetrieveAllowPipelineStageEvent> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final Tracer tracer;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final VerifysVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final VerifyRef reference) {
    Span startSpan = tracer.spanBuilder("verify-specific-allow-retrieve").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      VerifyRetrieveAllowPipelineStageEvent base = VerifyRetrieveAllowPipelineStageEvent
          .build(query, Optional.of(reference), true, "Allowed by default");
      retrieveAllow.fire(base);
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
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    Span startSpan = tracer.spanBuilder("verify-generic-allow-retrieve").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      VerifyRetrieveAllowPipelineStageEvent base = VerifyRetrieveAllowPipelineStageEvent
          .build(query, Optional.empty(), true, "Allowed by default");
      retrieveAllow.fire(base);
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
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<VerifyRetrieveResult> retrieve(final VerifyRetrieveQuery query) {
    Span startSpan = tracer.spanBuilder("verify-retrieve").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<VerifyCached> result =
          allow(query, query.getReference()).thenCompose(detail -> {
            if (!detail.isAllowed()) {
              throw new NotAllowedException(detail.getDescription());
            }
            return visibility.retrieveCachedVisible(query, query.getReference().getUidValue());
          });
      return result.thenCompose(op -> this.mapEntity(query, op)).whenComplete((val, ex) -> {
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
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param opverify
   * @return The slide with some values
   */
  private CompletionStage<VerifyRetrieveResult> mapEntity(final VerifyRetrieveQuery query,
      final VerifyCached opverify) {
    Span startSpan = tracer.spanBuilder("verify-map-retrieve-result").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return opverify.first()
          .map(verify -> visibility.copyWithHidden(query, verify)
              .thenApply(verifyWithHidden -> VerifyRetrieveResult.builder().interaction(query)
                  .verify(Optional.of(verifyWithHidden)).since(opverify.getSince()).build()))
          .orElseGet(() -> CompletableFuture.completedFuture(VerifyRetrieveResult.builder()
              .interaction(query).verify(Optional.empty()).since(OffsetDateTime.now()).build()))
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
