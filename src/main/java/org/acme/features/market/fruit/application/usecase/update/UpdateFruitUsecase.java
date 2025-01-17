package org.acme.features.market.fruit.application.usecase.update;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.application.usecase.update.event.FruitUpdateAllowPipelineStageEvent;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitWriteRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateFruitUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Fruits aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitCacheGateway cache;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitWriteRepositoryGateway gateway;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Tracer tracer;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<FruitUpdateAllowPipelineStageEvent> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final FruitRef reference) {
    Span startSpan = tracer.spanBuilder("fruit-update-allow-specific").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      FruitUpdateAllowPipelineStageEvent base = FruitUpdateAllowPipelineStageEvent.build(query,
          Optional.of(reference), true, "Allowed by default");
      updateAllow.fire(base);
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
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    Span startSpan = tracer.spanBuilder("fruit-update-allow-generic").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      FruitUpdateAllowPipelineStageEvent base = FruitUpdateAllowPipelineStageEvent.build(query,
          Optional.empty(), true, "Allowed by default");
      updateAllow.fire(base);
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
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<FruitUpdateResult> update(final FruitUpdateCommand command) {
    Span startSpan = tracer.spanBuilder("fruit-update").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Optional<Fruit>> updated =
          allow(command, command.getReference()).thenCompose(detail -> {
            if (!detail.isAllowed()) {
              throw new NotAllowedException(detail.getDescription());
            }
            return visibility.copyWithFixed(command, command.getDto())
                .thenCompose(builder -> visibility
                    .retrieveVisible(command, command.getReference().getUidValue())
                    .thenCompose(op -> saveIfIsPresent(op, builder)));
          });
      return updated.thenCompose(entity -> mapEntity(command, entity)).whenComplete((val, ex) -> {
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
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opfruit
   * @return The slide with some values
   */
  private CompletionStage<FruitUpdateResult> mapEntity(final FruitUpdateCommand command,
      final Optional<Fruit> opfruit) {
    Span startSpan = tracer.spanBuilder("fruit-map-updated-entity").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return opfruit
          .map(fruit -> visibility.copyWithHidden(command, fruit)
              .thenApply(visible -> FruitUpdateResult.builder().command(command)
                  .fruit(Optional.of(visible)).build()))
          .orElseGet(() -> CompletableFuture.completedStage(
              FruitUpdateResult.builder().command(command).fruit(Optional.empty()).build()))
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
   * @autogenerated UpdateUsecaseGenerator
   * @param original
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Fruit>> saveEntity(final Fruit original, final FruitDto dto) {
    Span startSpan = tracer.spanBuilder("fruit-run-updated-entity").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return aggregate.modify(original, dto.toEntityBuilder(Optional.of(original)))
          .thenCompose(fruit -> gateway.update(original, fruit))
          .thenCompose(updated -> cache.update(updated).thenApply(_ready -> Optional.of(updated)))
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
   * @autogenerated UpdateUsecaseGenerator
   * @param result
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Fruit>> saveIfIsPresent(final Optional<Fruit> result,
      final FruitDto dto) {
    Span startSpan = tracer.spanBuilder("fruit-load-to-update-if-present").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return result.map(original -> saveEntity(original, dto))
          .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()))
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
