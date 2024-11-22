package org.acme.features.fruit.domain.gateway;

import java.util.List;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;

public interface FruitsRepositoryGateway {
  Uni<List<Fruit>> fruits();
}
