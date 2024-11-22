package org.acme.features.fruit.infrastructure.driven;

import java.util.List;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;

public class FruitRepositorySlice implements FruitSlice {
  private final List<Fruit> fruits;
  private final FruitFilter filter;
  private final FruitCursor cursor;
  private final FruitsRepositoryGateway gateway;

  public FruitRepositorySlice(List<Fruit> fruits, FruitFilter filter, FruitCursor cursor, FruitsRepositoryGateway gateway) {
    super();
    this.fruits = fruits;
    this.gateway = gateway;
    this.filter = filter;
    this.cursor = cursor;
  }

  @Override
  public Uni<FruitSlice> next(int limit) {
    if( fruits.isEmpty() ) {
      return Uni.createFrom().item(this);
    } else {
      Fruit last = fruits.get( fruits.size() -1 );
      FruitCursor cr = this.cursor.toBuilder()
          .sinceUid( last.getUid() )
          .sinceName( last.getName() )
          .limit( limit )
          .build();
      return gateway.fruits(this.filter, cr);
    }
  }

  @Override
  public List<Fruit> getFruits() {
    return fruits;
  }
}
