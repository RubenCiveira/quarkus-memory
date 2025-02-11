package org.acme.features.market.place.application.usecase.retrieve;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.place.application.PlaceDto;
import org.acme.features.market.place.application.service.PlacesVisibilityService;
import org.acme.features.market.place.domain.gateway.PlaceCached;
import org.acme.features.market.place.domain.model.PlaceRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class RetrievePlaceUsecase {

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<PlaceRetrieveAllowProposal> retrieveAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final PlaceRef reference) {
    PlaceRetrieveAllowProposal base =
        PlaceRetrieveAllowProposal.build(query, Optional.of(reference), true, "Allowed by default");
    retrieveAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    PlaceRetrieveAllowProposal base =
        PlaceRetrieveAllowProposal.build(query, Optional.empty(), true, "Allowed by default");
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
  public PlaceDto retrieve(final PlaceRetrieveQuery query) {
    Allow detail = allow(query, query.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    PlaceCached retrieveCachedVisible =
        visibility.retrieveCachedVisible(query, query.getReference().getUidValue());
    return retrieveCachedVisible.first().map(first -> visibility.copyWithHidden(query, first))
        .orElseThrow(() -> new NotFoundException(""));
  }
}
