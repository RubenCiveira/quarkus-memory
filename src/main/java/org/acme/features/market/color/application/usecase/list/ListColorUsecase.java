package org.acme.features.market.color.application.usecase.list;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.application.usecase.list.event.ColorListAllowPipelineStageEvent;
import org.acme.features.market.color.domain.gateway.ColorCached;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ListColorUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<ColorListAllowPipelineStageEvent> listAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    ColorListAllowPipelineStageEvent base =
        ColorListAllowPipelineStageEvent.build(query, true, "Allowed by default");
    listAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<ColorListResult> list(final ColorListQuery query) {
    CompletionStage<ColorCached> future = allow(query).thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.listCachedVisibles(query, query.getFilter(), query.getCursor());
    });
    return future.thenCompose(values -> mapList(query, values));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param colors
   * @return The slide with some values
   */
  private CompletionStage<ColorListResult> mapList(final ColorListQuery query,
      final ColorCached colors) {
    List<CompletableFuture<ColorDto>> futures =
        colors.getValue().stream().map(color -> visibility.copyWithHidden(query, color))
            .map(CompletionStage::toCompletableFuture).toList();
    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenApply(voidResult -> ColorListResult.builder().query(query)
            .colors(futures.stream().map(CompletableFuture::join).toList()).since(colors.getSince())
            .build());
  }
}
