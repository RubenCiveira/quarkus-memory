package org.acme.features.market.place.application.usecase.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.place.application.service.PlacesVisibilityService;
import org.acme.features.market.place.application.usecase.create.event.PlaceCreateAllowPipelineStageEvent;
import org.acme.features.market.place.domain.Places;
import org.acme.features.market.place.domain.gateway.PlaceCacheGateway;
import org.acme.features.market.place.domain.gateway.PlaceWriteRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreatePlaceUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Places aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final PlaceCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<PlaceCreateAllowPipelineStageEvent> createAllow;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final PlaceWriteRepositoryGateway gateway;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Tracer tracer;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    Span startSpan = tracer.spanBuilder("place-create-allow").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      PlaceCreateAllowPipelineStageEvent base =
          PlaceCreateAllowPipelineStageEvent.build(query, true, "Allowed by default");
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
  public CompletionStage<PlaceCreateResult> create(final PlaceCreateCommand query) {
    Span startSpan = tracer.spanBuilder("place-create").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Optional<Place>> create = allow(query).thenCompose(detail -> {
        if (!detail.isAllowed()) {
          throw new NotAllowedException(detail.getDescription());
        }
        return visibility.copyWithFixed(query, query.getDto())
            .thenCompose(builder -> aggregate.initialize(builder.toEntityBuilder(Optional.empty()))
                .thenCompose(placeEntity -> createAndVerify(query, placeEntity)));
      });
      return create.thenCompose(place -> mapEntity(query, place)).whenComplete((val, ex) -> {
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
   * @param placeEntity
   * @return
   */
  private CompletionStage<Optional<Place>> createAndVerify(final PlaceCreateCommand query,
      final Place placeEntity) {
    Span startSpan = tracer.spanBuilder("place-run-create-and-verify").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return gateway
          .create(placeEntity, created -> visibility.checkVisibility(query, created.getUidValue()))
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
   * @param opplace
   * @return The slide with some values
   */
  private CompletionStage<PlaceCreateResult> mapEntity(final PlaceCreateCommand command,
      final Optional<Place> opplace) {
    Span startSpan = tracer.spanBuilder("place-map-created-entity").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return opplace
          .map(place -> visibility.copyWithHidden(command, place)
              .thenApply(visible -> PlaceCreateResult.builder().command(command)
                  .place(Optional.of(visible)).build()))
          .orElseGet(() -> CompletableFuture.completedStage(
              PlaceCreateResult.builder().command(command).place(Optional.empty()).build()))
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
