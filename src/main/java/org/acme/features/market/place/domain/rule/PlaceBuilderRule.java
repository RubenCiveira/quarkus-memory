package org.acme.features.market.place.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.Place.PlaceBuilder;

public interface PlaceBuilderRule
    extends ParametrizedPipe<CompletionStage<PlaceBuilder>, PlaceActionType, Optional<Place>> {
}
