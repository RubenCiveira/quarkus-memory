package org.acme.features.market.color.application.usecase.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.application.usecase.create.event.ColorCreateAllowPipelineStageEvent;
import org.acme.features.market.color.domain.Colors;
import org.acme.features.market.color.domain.gateway.ColorCacheGateway;
import org.acme.features.market.color.domain.gateway.ColorWriteRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class CreateColorUsecase {

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final Colors aggregate;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final ColorCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated CreateUsecaseGenerator
   */
  private final Event<ColorCreateAllowPipelineStageEvent> createAllow;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final ColorWriteRepositoryGateway gateway;

  /**
   * @autogenerated CreateUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @return
   */
  public CompletionStage<Allow> allow(final Interaction query) {
    ColorCreateAllowPipelineStageEvent base =
        ColorCreateAllowPipelineStageEvent.build(query, true, "Allowed by default");
    createAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<ColorCreateResult> create(final ColorCreateCommand query) {
    CompletionStage<Optional<Color>> create = allow(query).thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.copyWithFixed(query, query.getDto())
          .thenCompose(builder -> aggregate.initialize(builder.toEntityBuilder(Optional.empty()))
              .thenCompose(colorEntity -> createAndVerify(query, colorEntity)));
    });
    return create.thenCompose(color -> mapEntity(query, color));
  }

  /**
   * @autogenerated CreateUsecaseGenerator
   * @param query
   * @param colorEntity
   * @return
   */
  private CompletionStage<Optional<Color>> createAndVerify(final ColorCreateCommand query,
      final Color colorEntity) {
    return gateway
        .create(colorEntity, created -> visibility.checkVisibility(query, created.getUidValue()))
        .thenCompose(validated -> validated
            .map(created -> cache.update(created).thenApply(_ready -> Optional.of(created)))
            .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())));
  }

  /**
   * The slide with some values
   *
   * @autogenerated CreateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opcolor
   * @return The slide with some values
   */
  private CompletionStage<ColorCreateResult> mapEntity(final ColorCreateCommand command,
      final Optional<Color> opcolor) {
    return opcolor
        .map(color -> visibility.copyWithHidden(command, color)
            .thenApply(visible -> ColorCreateResult.builder().command(command)
                .color(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            ColorCreateResult.builder().command(command).color(Optional.empty()).build()));
  }
}
