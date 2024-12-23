package org.acme.features.market.color.application.usecase;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.color.application.allow.ColorRetrieveAllow;
import org.acme.features.market.color.application.interaction.ColorDto;
import org.acme.features.market.color.application.interaction.query.ColorEntityAllowQuery;
import org.acme.features.market.color.application.interaction.query.ColorRetrieveQuery;
import org.acme.features.market.color.application.interaction.result.ColorRetrieveResult;
import org.acme.features.market.color.application.usecase.service.ColorsVisibilityService;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.gateway.ColorReadRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrieveColorUsecase {

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final ColorReadRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<ColorRetrieveAllow> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public ColorRetrieveAllow allow(final ColorEntityAllowQuery query) {
    ColorRetrieveAllow base =
        ColorRetrieveAllow.build(query.getReference(), true, "Allowed by default");
    retrieveAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public ColorRetrieveResult retrieve(final ColorRetrieveQuery query) {
    CompletionStage<Optional<Color>> result = allow(query).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      ColorFilter filter = ColorFilter.builder().uid(query.getReference().getUidValue()).build();
      return visibility.visibleFilter(query, filter).thenCompose(visibleFilter -> gateway
          .retrieve(query.getReference().getUidValue(), Optional.of(visibleFilter)));
    });
    return ColorRetrieveResult.builder().interaction(query)
        .color(result.thenCompose(op -> this.mapEntity(query, op))).build();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  private ColorRetrieveAllow allow(final ColorRetrieveQuery query) {
    return allow(ColorEntityAllowQuery.builder().reference(query.getReference()).build(query));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param opcolor
   * @return The slide with some values
   */
  private CompletionStage<Optional<ColorDto>> mapEntity(final ColorRetrieveQuery query,
      final Optional<Color> opcolor) {
    return opcolor.map(color -> visibility.hide(query, color).thenApply(Optional::of))
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }
}
