package org.acme.features.market.color.application.usecase.retrieve;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.application.usecase.retrieve.event.ColorRetrieveAllowPipelineStageEvent;
import org.acme.features.market.color.domain.gateway.ColorCached;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrieveColorUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<ColorRetrieveAllowPipelineStageEvent> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query, final ColorRef reference) {
    ColorRetrieveAllowPipelineStageEvent base = ColorRetrieveAllowPipelineStageEvent.build(query,
        Optional.of(reference), true, "Allowed by default");
    retrieveAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    ColorRetrieveAllowPipelineStageEvent base = ColorRetrieveAllowPipelineStageEvent.build(query,
        Optional.empty(), true, "Allowed by default");
    retrieveAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<ColorRetrieveResult> retrieve(final ColorRetrieveQuery query) {
    CompletionStage<ColorCached> result = allow(query, query.getReference()).thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.retrieveCachedVisible(query, query.getReference().getUidValue());
    });
    return result.thenCompose(op -> this.mapEntity(query, op));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param opcolor
   * @return The slide with some values
   */
  private CompletionStage<ColorRetrieveResult> mapEntity(final ColorRetrieveQuery query,
      final ColorCached opcolor) {
    return opcolor.first()
        .map(color -> visibility.copyWithHidden(query, color)
            .thenApply(colorWithHidden -> ColorRetrieveResult.builder().interaction(query)
                .color(Optional.of(colorWithHidden)).since(opcolor.getSince()).build()))
        .orElseGet(() -> CompletableFuture.completedFuture(ColorRetrieveResult.builder()
            .interaction(query).color(Optional.empty()).since(OffsetDateTime.now()).build()));
  }
}
