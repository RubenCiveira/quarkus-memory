package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.sql.SqlCommand;
import org.acme.common.sql.SqlOperator;
import org.acme.common.sql.SqlParameterValue;
import org.acme.common.sql.SqlSchematicQuery;
import org.acme.common.sql.SqlTemplate;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitRepository implements FruitRepositoryGateway {
  private final DataSource datasource;

  @Override
  public Slide<Fruit> list(FruitFilter filter, FruitCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Fruit> sq = template.createSqlSchematicQuery("fruits");
      sq.select("uid", "name", "version");
      cursor.getSinceUid()
          .ifPresent(since -> sq.where("uid", SqlOperator.GT, SqlParameterValue.of(since)));
      sq.orderAsc("uid");
      return new FruitRepositorySlice(cursor.getLimit(),
          sq.query(row -> Fruit.builder().uid(FruitUidVO.from(row.getString(1)))
              .name(FruitNameVO.from(row.getString(2))).version(FruitVersionVO.from(row.getInt(3)))
              .build()).limit(cursor.getLimit()),
          filter, cursor, this);
    }
  }

  @Override
  public CompletableFuture<Optional<Fruit>> retrieve(String uid, Optional<FruitFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Fruit> sq = template.createSqlSchematicQuery("fruits");
      sq.select("uid", "name", "version");
      sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid));
      sq.orderAsc("uid");
      return CompletableFuture.completedFuture(sq.query(row -> Fruit.builder()
          .uid(FruitUidVO.from(row.getString(1))).name(FruitNameVO.from(row.getString(2)))
          .version(FruitVersionVO.from(row.getInt(3))).build()).one());
    }
  }

  @Override
  public CompletableFuture<Boolean> exists(String uid, Optional<FruitFilter> filter) {
    return retrieve(uid, filter).thenApply(Optional::isPresent);
  }

  @Override
  @Transactional
  public CompletableFuture<Optional<Fruit>> create(Fruit entity,
      Function<Fruit, CompletableFuture<Boolean>> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into fruits (uid, name, version) values (:uid, :name, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUid().getValue()));
      sq.with("name", SqlParameterValue.of(entity.getName().getValue()));
      sq.with("version", SqlParameterValue.of(entity.getVersion().getValue().orElse(0)));
      if (0 == sq.execute()) {
        throw new IllegalArgumentException("No insert into");
      }
      return verifier.apply(entity).thenCompose(exists -> {
        if (exists) {
          return CompletableFuture.completedFuture(Optional.of(entity));
        } else {
          template.createSqlCommand("delete from fruits where uid = :uid")
              .with("uid", SqlParameterValue.of(entity.getUid().getValue())).execute();
          return CompletableFuture.completedFuture(Optional.empty());
        }
      });
    }
  }
  //
  // @Override
  // public Uni<Optional<Fruit>> retrieve(FruitFilter filter) {
  // try (var template = new SqlTemplate(datasource)) {
  // SqlResult<Fruit> result =
  // template.query("select uid, name, version from fruits where uid = :uid",
  // row -> Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
  // .versionValue(row.getInt(3)).build(),
  // new SqlParam("uid", filter.getUid().get(), SqlType.STRING));
  // return Uni.createFrom().item(result.one());
  // }
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> update(String uid, Fruit entity) {
  // return null;
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> create(Fruit entity) {
  // return createAndVerify(entity, null);
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> createAndVerify(Fruit entity, Function<Fruit, Boolean> verificator)
  // {
  // try (var template = new SqlTemplate(datasource)) {
  // template.execute("insert into fruits(uid, name, version) values (:uid, :name, 0)",
  // new SqlParam("uid", entity.getUid().getValue(), SqlType.STRING),
  // new SqlParam("name", entity.getName().getValue(), SqlType.STRING));
  // template.commit();
  // if (null == verificator || verificator.apply(entity)) {
  // return Uni.createFrom().item(Optional.of(entity));
  // } else {
  // rollback(entity);
  // return Uni.createFrom().item(Optional.empty());
  // }
  // }
  // }
  //
  // private Uni<Void> rollback(Fruit entity) {
  // try (var template = new SqlTemplate(datasource)) {
  // template.execute("delete from fruits where uid = :uid",
  // new SqlParam("uid", entity.getUid(), SqlType.STRING));
  // return Uni.createFrom().voidItem();
  // }
  // }
}
