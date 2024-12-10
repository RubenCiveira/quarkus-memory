package org.acme.features.market.fruit.infrastructure.driven;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.sql.OptimistLockException;
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
  public CompletableFuture<Fruit> delete(Fruit entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template
          .createSqlCommand("delete from fruits where uid = :uid");
      sq.with("uid", SqlParameterValue.of(entity.getUid().getValue()));
      if (0 == sq.execute()) {
        throw new IllegalArgumentException("No delete from");
      }
      return CompletableFuture.completedFuture(entity);
    }
  }

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
  public CompletableFuture<Optional<Fruit>> create(Fruit entity) {
    return doCreate(entity, null);
  }

  @Override
  @Transactional
  public CompletableFuture<Optional<Fruit>> create(Fruit entity,
      Function<Fruit, CompletableFuture<Boolean>> verifier) {
    return doCreate(entity, verifier);
  }

  @Override
  @Transactional
  public CompletableFuture<Fruit> update(Fruit entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersion().getValue().orElse(0);
      SqlCommand sq =
          template.createSqlCommand("update fruits set name = :name, version = version + 1 "
              + " where uid = :uid and version = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUid().getValue()));
      sq.with("name", SqlParameterValue.of(entity.getName().getValue()));
      sq.with("version", SqlParameterValue.of(version));
      if (0 == sq.execute()) {
        throw new OptimistLockException();
      }
      return CompletableFuture
          .completedFuture(entity.withVersion(FruitVersionVO.from(version + 1)));
    }
  }

  public CompletableFuture<Optional<Fruit>> doCreate(Fruit entity,
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
      return verifier == null ? CompletableFuture.completedFuture(Optional.of(entity))
          : verifier.apply(entity).thenCompose(exists -> {
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
}
