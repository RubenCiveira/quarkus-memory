package org.acme.features.market.place.domain.rule;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.ParametrizedPipe;
import org.acme.features.market.place.domain.model.Place;

public interface PlaceRule
    extends ParametrizedPipe<CompletionStage<Place>, PlaceActionType, Optional<Place>> {
}
