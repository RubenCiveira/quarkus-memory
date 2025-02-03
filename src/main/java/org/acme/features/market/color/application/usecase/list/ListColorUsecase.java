package org.acme.features.market.color.application.usecase.list;

import java.util.List;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
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
  private final Event<ColorListAllowPipelineEvent> listAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    ColorListAllowPipelineEvent base =
        ColorListAllowPipelineEvent.build(query, true, "Allowed by default");
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
  public List<ColorDto> list(final ColorListQuery query) {
    Allow detail = allow(query);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    ColorCached values = visibility.listCachedVisibles(query, query.getFilter(), query.getCursor());
    return values.getValue().stream().map(value -> visibility.copyWithHidden(query, value))
        .toList();
  }
}
