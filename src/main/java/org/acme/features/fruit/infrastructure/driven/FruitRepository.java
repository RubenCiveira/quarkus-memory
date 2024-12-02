package org.acme.features.fruit.infrastructure.driven;

import java.util.List;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.sql.SqlTemplate;
import org.acme.features.fruit.domain.gateway.FruitsRepositoryGateway;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.model.Fruit;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitRepository implements FruitsRepositoryGateway {
  private final DataSource datasource;


  public Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor) {
    try (var template = new SqlTemplate(datasource)) {
      List<Fruit> list = template.query("select uid, name from fruits", row -> {
        return Fruit.builder().uid(row.getString(1)).name(row.getString(2)).build();
      });
      return Uni.createFrom().item(new FruitRepositorySlice(list, filter, cursor, this));
    }
    /*
     * Stream<Fruit> tfruits = fruits.stream(); System.err.println("Call to repository"); if (null
     * != cursor.getSinceUid()) { System.err.println("\tSince " + cursor.getSinceUid() ); tfruits =
     * tfruits.filter(fruit -> fruit.getUid().compareTo(cursor.getSinceUid()) > 0 ); } if (null !=
     * cursor.getLimit()) { System.err.println("\tLimite: " + cursor.getLimit() ); tfruits =
     * tfruits.limit(cursor.getLimit()); } List<Fruit> list = tfruits.toList();
     * System.err.println("\tReaded: " + list.size() ); return Uni.createFrom().item(new
     * FruitRepositorySlice(list, filter, cursor, this));
     */
  }
}
