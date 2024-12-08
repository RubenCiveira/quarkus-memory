package org.acme.features.market.templatefruit.domain;

import org.acme.features.market.fruit.domain.gateway.FruitRepositoryGateway;
import org.acme.features.market.fruit.domain.interaction.FruitDto;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FruitsUsecase {
  private final Fruits fruits;
  private final FruitRepositoryGateway gateway;

  public void create(FruitDto dto) {
    Fruit initilize = fruits.initilize(dto.toBuilder());
    gateway.createAndVerify(initilize, (entity) -> true);
  }

  public void update(String uid, FruitDto dto) {
    FruitFilter filter = FruitFilter.builder().uid(uid).build();
    gateway.retrieve(filter).flatMap(uni -> uni.map(original -> {
      Fruit value = fruits.modify(original, dto.toBuilder());
      return gateway.update(uid, value);
    }).orElse(Uni.createFrom().nullItem()));
  }
}
