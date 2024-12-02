package org.acme.features.market.fruit.infrastructure.driven;

import java.util.List;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.sql.SqlTemplate;
import org.acme.features.market.fruit.domain.gateway.FruitRepositoryGateway;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.model.Fruit;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitRepository implements FruitRepositoryGateway {
  private final DataSource datasource;


  @Override
  public Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor) {
    try (var template = new SqlTemplate(datasource)) {
      List<Fruit> list = template.query("select uid, name, version from fruits", row -> {
        return Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
            .versionValue(row.getInt(3)).build();
      });
      return Uni.createFrom().item(new FruitRepositorySlice(list, filter, cursor, this));
    }
  }
}
