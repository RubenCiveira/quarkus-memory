package org.acme.features.market.color.infrastructure.repository;

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
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ColorRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Optional<Color>> create(Color entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public CompletionStage<Optional<Color>> create(Color entity,
      Function<Color, CompletionStage<Boolean>> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Color> delete(Color entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"color\" where \"uid\" = :uid");
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
  public CompletionStage<Color> enrich(ColorRef reference) {
    return reference instanceof Color ? CompletableFuture.completedStage((Color) reference)
        : retrieve(reference.getUidValue(), Optional.empty())
            .thenApply(optional -> optional.orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Color: " + reference.getUidValue())));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Boolean> exists(String uid, Optional<ColorFilter> filter) {
    return retrieve(uid, filter).thenApply(Optional::isPresent);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Slide<Color>> list(ColorFilter filter, ColorCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Color> sq = filteredQuery(template, filter);
      cursor.getSinceUid()
          .ifPresent(since -> sq.where("uid", SqlOperator.GT, SqlParameterValue.of(since)));
      sq.orderAsc("uid");
      return sq.query(converter()).thenApply(res -> new ColorSlice(cursor.getLimit(),
          res.limit(cursor.getLimit()), this::list, filter, cursor));
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Optional<Color>> retrieve(String uid, Optional<ColorFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      ColorFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> ColorFilter.builder().uid(uid).build());
      SqlSchematicQuery<Color> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).thenApply(SqlResult::one);
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Color> update(Color entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"color\" set  \"name\" = :name, \"merchant\" = :merchant, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("merchant", entity.getMerchantReferenceValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullString));
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
  private SqlConverter<Color> converter() {
    return (row) -> {
      try {
        return Optional.of(Color.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
            .merchantReferenceValue(row.getString(3)).versionValue(row.getInt(4)).build());
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
  private SqlSchematicQuery<Color> filteredQuery(SqlTemplate template, ColorFilter filter) {
    SqlSchematicQuery<Color> sq = template.createSqlSchematicQuery("color");
    sq.select("color.uid", "color.name", "color.merchant", "color.version");
    filter.getUid().ifPresent(uid -> sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid)));
    if (!filter.getUids().isEmpty()) {
      sq.where("uid", SqlOperator.IN, SqlListParameterValue.strings(filter.getUids()));
    }
    filter.getSearch().ifPresent(
        search -> sq.where("name", SqlOperator.LIKE, SqlParameterValue.of("%" + search + "%")));
    filter.getMerchant().ifPresent(merchant -> sq.where("merchant", SqlOperator.EQ,
        SqlParameterValue.of(merchant.getUidValue())));
    filter.getMerchantMerchantAccesible().ifPresent(merchantMerchantAccesible -> {
      sq.where("color.merchant", SqlOperator.EQ, SqlParameterValue.of(merchantMerchantAccesible));
    });
    return sq;
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  private CompletionStage<Optional<Color>> runCreate(Color entity,
      Function<Color, CompletionStage<Boolean>> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"color\" ( \"uid\", \"name\", \"merchant\", \"version\") values ( :uid, :name, :merchant, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("merchant", entity.getMerchantReferenceValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullString));
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
                template.createSqlCommand("delete from \"color\" where \"uid\" = :uid")
                    .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
                return CompletableFuture.completedFuture(Optional.empty());
              }
            });
      });
    }
  }
}
