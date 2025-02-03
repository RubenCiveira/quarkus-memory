package org.acme.features.market.place.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.place.domain.model.Place;

public interface PlaceRule extends ParametrizedPipe<Place, PlaceActionType, Optional<Place>> {
}
