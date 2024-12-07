package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.sql.SqlParam;
import org.acme.common.sql.SqlResult;
import org.acme.common.sql.SqlTemplate;
import org.acme.common.sql.SqlType;
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
      SqlResult<Fruit> result = template.query("select uid, name, version from fruits",
          row -> Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
              .versionValue(row.getInt(3)).build());
      return Uni.createFrom()
          .item(new FruitRepositorySlice(
              cursor.getLimit().map(limit -> result.limit(limit)).orElseGet(() -> result.all()),
              filter, cursor, this));
    }
  }

  @Override
  public Uni<Optional<Fruit>> retrieve(FruitFilter filter) {
    try (var template = new SqlTemplate(datasource)) {
      SqlResult<Fruit> result =
          template.query("select uid, name, version from fruits where uid = :uid",
              row -> Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
                  .versionValue(row.getInt(3)).build(),
              new SqlParam("uid", filter.getUid().get(), SqlType.STRING));
      return Uni.createFrom().item(result.one());
    }
  }
}
