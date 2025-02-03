package org.acme.features.market.place.domain.rule;

import java.util.Optional;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.Place.PlaceBuilder;

public interface PlaceBuilderRule
    extends ParametrizedPipe<PlaceBuilder, PlaceActionType, Optional<Place>> {
}
