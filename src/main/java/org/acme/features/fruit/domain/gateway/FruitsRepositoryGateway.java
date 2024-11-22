package org.acme.features.fruit.domain.gateway;

import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import io.smallrye.mutiny.Uni;

public interface FruitsRepositoryGateway {
  Uni<FruitSlice> fruits(FruitFilter filter, FruitCursor cursor);
}
