package org.acme.features.market.medal.infrastructure.repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.exception.ConstraintException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.sql.OptimistLockException;
import org.acme.common.sql.SqlCommand;
import org.acme.common.sql.SqlConverter;
import org.acme.common.sql.SqlListParameterValue;
import org.acme.common.sql.SqlOperator;
import org.acme.common.sql.SqlParameterValue;
import org.acme.common.sql.SqlResult;
import org.acme.common.sql.SqlSchematicQuery;
import org.acme.common.sql.SqlTemplate;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MedalRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Optional<Medal>> create(Medal entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public CompletionStage<Optional<Medal>> create(Medal entity,
      Function<Medal, CompletionStage<Boolean>> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Medal> delete(Medal entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"medal\" where \"uid\" = :uid");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      return sq.execute().thenApply(num -> {
        if (0 == num) {
          throw new IllegalArgumentException("No delete from");
        }
        return entity;
      });
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param reference
   * @return
   */
  public CompletionStage<Medal> enrich(MedalRef reference) {
    return reference instanceof Medal ? CompletableFuture.completedStage((Medal) reference)
        : retrieve(reference.getUidValue(), Optional.empty())
            .thenApply(optional -> optional.orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Medal: " + reference.getUidValue())));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Boolean> exists(String uid, Optional<MedalFilter> filter) {
    return retrieve(uid, filter).thenApply(Optional::isPresent);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Slide<Medal> list(MedalFilter filter, MedalCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Medal> sq = filteredQuery(template, filter);
      cursor.getSinceUid()
          .ifPresent(since -> sq.where("uid", SqlOperator.GT, SqlParameterValue.of(since)));
      sq.orderAsc("uid");
      return new MedalSlice(cursor.getLimit(),
          sq.query(converter()).thenApply(res -> res.limit(cursor.getLimit())), this::list, filter,
          cursor);
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Optional<Medal>> retrieve(String uid, Optional<MedalFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      MedalFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> MedalFilter.builder().uid(uid).build());
      SqlSchematicQuery<Medal> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).thenApply(SqlResult::one);
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Medal> update(Medal entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"medal\" set  \"name\" = :name, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("version", entity.getVersionValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullInteger));
      return sq.execute().thenApply(num -> {
        if (0 == num) {
          throw new OptimistLockException("No delete from");
        }
        return entity.withVersionValue(version + 1);
      });
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @return
   */
  private SqlConverter<Medal> converter() {
    return (row) -> {
      try {
        return Optional.of(Medal.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
            .versionValue(row.getInt(3)).build());
      } catch (ConstraintException ce) {
        log.error("Unable to map data for {}", row.getString(1), ce);
        return Optional.empty();
      }
    };
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param template
   * @param filter
   * @return
   */
  private SqlSchematicQuery<Medal> filteredQuery(SqlTemplate template, MedalFilter filter) {
    SqlSchematicQuery<Medal> sq = template.createSqlSchematicQuery("medal");
    sq.select("medal.uid", "medal.name", "medal.version");
    filter.getUid().ifPresent(uid -> sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid)));
    if (!filter.getUids().isEmpty()) {
      sq.where("uid", SqlOperator.IN, SqlListParameterValue.strings(filter.getUids()));
    }
    filter.getSearch().ifPresent(
        search -> sq.where("name", SqlOperator.LIKE, SqlParameterValue.of("%" + search + "%")));
    return sq;
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  private CompletionStage<Optional<Medal>> runCreate(Medal entity,
      Function<Medal, CompletionStage<Boolean>> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"medal\" ( \"uid\", \"name\", \"version\") values ( :uid, :name, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("version", entity.getVersionValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullInteger));
      return sq.execute().thenCompose(num -> {
        if (0 == num) {
          throw new IllegalArgumentException("No insert into");
        }
        return verifier == null ? CompletableFuture.completedFuture(Optional.of(entity))
            : verifier.apply(entity).thenCompose(exists -> {
              if (exists) {
                return CompletableFuture.completedFuture(Optional.of(entity));
              } else {
                template.createSqlCommand("delete from \"medal\" where \"uid\" = :uid")
                    .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
                return CompletableFuture.completedFuture(Optional.empty());
              }
            });
      });
    }
  }
}
