package org.acme.features.market.place.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.Place.PlaceBuilder;

public interface PlaceBuilderRule
    extends ParametrizedPipe<CompletableFuture<PlaceBuilder>, PlaceActionType, Optional<Place>> {
}
