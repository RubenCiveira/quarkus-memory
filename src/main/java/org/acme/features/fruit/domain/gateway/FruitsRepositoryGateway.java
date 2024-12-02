package org.acme.features.fruit.domain.gateway;

import org.acme.common.action.Slide;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.model.Fruit;

import io.smallrye.mutiny.Uni;

public interface FruitsRepositoryGateway {
  Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor);
}
