package org.acme.features.market.color.application.usecase;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.color.application.allow.ColorUpdateAllow;
import org.acme.features.market.color.application.interaction.ColorDto;
import org.acme.features.market.color.application.interaction.command.ColorUpdateCommand;
import org.acme.features.market.color.application.interaction.query.ColorEntityAllowQuery;
import org.acme.features.market.color.application.interaction.result.ColorUpdateResult;
import org.acme.features.market.color.application.usecase.service.ColorsVisibilityService;
import org.acme.features.market.color.domain.Colors;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.gateway.ColorWriteRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class UpdateColorUsecase {

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Colors aggregate;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final ColorWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated UpdateUsecaseGenerator
   */
  private final Event<ColorUpdateAllow> updateAllow;

  /**
   * @autogenerated UpdateUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  public ColorUpdateAllow allow(final ColorEntityAllowQuery query) {
    ColorUpdateAllow base =
        ColorUpdateAllow.build(query.getReference(), true, "Allowed by default");
    updateAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @return
   */
  public ColorUpdateAllow allow() {
    return null;
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public ColorUpdateResult update(final ColorUpdateCommand command) {
    CompletionStage<Optional<Color>> updated = allow(command).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.copyWithFixed(command, command.getDto()).thenCompose(builder -> {
        ColorFilter filter =
            ColorFilter.builder().uid(command.getReference().getUidValue()).build();
        return retrieve(command, filter).thenCompose(op -> saveIfIsPresent(op, builder));
      });
    });
    return ColorUpdateResult.builder().command(command)
        .color(updated.thenCompose(entity -> mapEntity(command, entity))).build();
  }

  /**
   * @autogenerated UpdateUsecaseGenerator
   * @param query
   * @return
   */
  private ColorUpdateAllow allow(final ColorUpdateCommand query) {
    return allow(ColorEntityAllowQuery.builder().reference(query.getReference()).build(query));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opcolor
   * @return The slide with some values
   */
  private CompletionStage<Optional<ColorDto>> mapEntity(final ColorUpdateCommand command,
      final Optional<Color> opcolor) {
    return opcolor.map(color -> visibility.hide(command, color).thenApply(Optional::of))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param filter a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Color>> retrieve(final ColorUpdateCommand command,
      final ColorFilter filter) {
    return visibility.visibleFilter(command, filter).thenCompose(visibleFilter -> gateway
        .retrieve(command.getReference().getUidValue(), Optional.of(visibleFilter)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param original
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Color>> saveEntity(final Color original, final ColorDto dto) {
    return aggregate.modify(original, dto.toEntityBuilder())
        .thenCompose(color -> gateway.update(color).thenApply(Optional::of));
  }

  /**
   * The slide with some values
   *
   * @autogenerated UpdateUsecaseGenerator
   * @param result
   * @param dto a filter to retrieve only matching values
   * @return The slide with some values
   */
  private CompletionStage<Optional<Color>> saveIfIsPresent(final Optional<Color> result,
      final ColorDto dto) {
    return result.map(original -> saveEntity(original, dto))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
