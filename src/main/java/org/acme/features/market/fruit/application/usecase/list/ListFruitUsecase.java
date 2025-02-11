package org.acme.features.market.fruit.application.usecase.list;

import java.util.List;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.metadata.TimestampedList;
import org.acme.common.metadata.WrapMetadata;
import org.acme.common.security.Allow;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.gateway.FruitCached;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ListFruitUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<FruitListAllowProposal> listAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    FruitListAllowProposal base = FruitListAllowProposal.build(query, true, "Allowed by default");
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
  public List<FruitDto> list(final FruitListQuery query) {
    Allow detail = allow(query);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    FruitCached values = visibility.listCachedVisibles(query, query.getFilter(), query.getCursor());
    List<FruitDto> list =
        values.getValue().stream().map(value -> visibility.copyWithHidden(query, value)).toList();
    return new TimestampedList<>(WrapMetadata.<List<FruitDto>>builder().data(list)
        .since(values.getSince().toInstant()).build());
  }
}
