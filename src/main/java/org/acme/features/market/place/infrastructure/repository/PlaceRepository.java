package org.acme.features.market.place.infrastructure.repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.action.Slide;
import org.acme.common.exception.ConstraintException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.sql.OptimistLockException;
import org.acme.common.sql.PartialWhere;
import org.acme.common.sql.SqlCommand;
import org.acme.common.sql.SqlConverter;
import org.acme.common.sql.SqlListParameterValue;
import org.acme.common.sql.SqlOperator;
import org.acme.common.sql.SqlParameterValue;
import org.acme.common.sql.SqlSchematicQuery;
import org.acme.common.sql.SqlTemplate;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlaceOrder;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PlaceRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Optional<Place>> create(Place entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public CompletionStage<Optional<Place>> create(Place entity,
      Function<Place, CompletionStage<Boolean>> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Place> delete(Place entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"place\" where \"uid\" = :uid");
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
  public CompletionStage<Place> enrich(PlaceRef reference) {
    return reference instanceof Place ? CompletableFuture.completedStage((Place) reference)
        : retrieve(reference.getUidValue(), Optional.empty())
            .thenApply(optional -> optional.orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Place: " + reference.getUidValue())));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Boolean> exists(String uid, Optional<PlaceFilter> filter) {
    return retrieve(uid, filter).thenApply(Optional::isPresent);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public CompletionStage<Slide<Place>> list(PlaceFilter filter, PlaceCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Place> sq = filteredQuery(template, filter);
      PartialWhere offset = PartialWhere.empty();
      PartialWhere prev = PartialWhere.empty();
      if (null != cursor.getOrder()) {
        for (PlaceOrder order : cursor.getOrder()) {
          if (order == PlaceOrder.NAME_ASC) {
            sq.orderAsc("name");
            Optional<String> sinceName = cursor.getSinceName();
            if (sinceName.isPresent()) {
              String sinceNameValue = sinceName.get();
              offset = PartialWhere.or(offset, PartialWhere.and(prev, PartialWhere.where("name",
                  SqlOperator.GT, SqlParameterValue.of(sinceNameValue))));
              prev = PartialWhere.and(prev,
                  PartialWhere.where("name", SqlOperator.EQ, SqlParameterValue.of(sinceNameValue)));
            }
          }
          if (order == PlaceOrder.NAME_DESC) {
            sq.orderDesc("name");
            Optional<String> sinceName = cursor.getSinceName();
            if (sinceName.isPresent()) {
              String sinceNameValue = sinceName.get();
              offset = PartialWhere.or(offset, PartialWhere.and(prev, PartialWhere.where("name",
                  SqlOperator.GT, SqlParameterValue.of(sinceNameValue))));
              prev = PartialWhere.and(prev,
                  PartialWhere.where("name", SqlOperator.EQ, SqlParameterValue.of(sinceNameValue)));
            }
          }
        }
        Optional<String> sinceUid = cursor.getSinceUid();
        if (sinceUid.isPresent()) {
          offset = PartialWhere.or(offset, PartialWhere.and(prev,
              PartialWhere.where("uid", SqlOperator.GT, SqlParameterValue.of(sinceUid.get()))));
        }
        sq.where(offset);
      } else {
        cursor.getSinceUid()
            .ifPresent(since -> sq.where("uid", SqlOperator.GT, SqlParameterValue.of(since)));
      }
      sq.orderAsc("uid");
      return sq.query(converter()).limit(cursor.getLimit())
          .thenApply(res -> new PlaceSlice(cursor.getLimit(), res, this::list, filter, cursor));
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public CompletionStage<Optional<Place>> retrieve(String uid, Optional<PlaceFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      PlaceFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> PlaceFilter.builder().uid(uid).build());
      SqlSchematicQuery<Place> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).one();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public CompletionStage<Place> update(Place entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"place\" set  \"name\" = :name, \"merchant\" = :merchant, \"photo\" = :photo, \"opening_date\" = :openingDate, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("merchant", SqlParameterValue.of(entity.getMerchantReferenceValue()));
      sq.with("photo", entity.getPhotoValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullString));
      sq.with("openingDate", entity.getOpeningDateValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullOffsetDateTime));
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
  private SqlConverter<Place> converter() {
    return (row) -> {
      try {
        return Optional.of(Place.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
            .merchantReferenceValue(row.getString(3)).photoValue(row.getString(4))
            .openingDateValue(OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(row.getTimestamp(5).getTime()), ZoneId.systemDefault()))
            .versionValue(row.getInt(6)).build());
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
  private SqlSchematicQuery<Place> filteredQuery(SqlTemplate template, PlaceFilter filter) {
    SqlSchematicQuery<Place> sq = template.createSqlSchematicQuery("place");
    sq.select("place.uid", "place.name", "place.merchant", "place.photo", "place.opening_date",
        "place.version");
    filter.getUid().ifPresent(uid -> sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid)));
    if (!filter.getUids().isEmpty()) {
      sq.where("uid", SqlOperator.IN, SqlListParameterValue.strings(filter.getUids()));
    }
    filter.getSearch().ifPresent(
        search -> sq.where("name", SqlOperator.LIKE, SqlParameterValue.of("%" + search + "%")));
    filter.getName()
        .ifPresent(name -> sq.where("name", SqlOperator.EQ, SqlParameterValue.of(name)));
    filter.getMerchant().ifPresent(merchant -> sq.where("merchant", SqlOperator.EQ,
        SqlParameterValue.of(merchant.getUidValue())));
    filter.getMerchantMerchantAccesible().ifPresent(merchantMerchantAccesible -> {
      sq.where("place.merchant", SqlOperator.EQ, SqlParameterValue.of(merchantMerchantAccesible));
    });
    return sq;
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  private CompletionStage<Optional<Place>> runCreate(Place entity,
      Function<Place, CompletionStage<Boolean>> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"place\" ( \"uid\", \"name\", \"merchant\", \"photo\", \"opening_date\", \"version\") values ( :uid, :name, :merchant, :photo, :openingDate, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("merchant", SqlParameterValue.of(entity.getMerchantReferenceValue()));
      sq.with("photo", entity.getPhotoValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullString));
      sq.with("openingDate", entity.getOpeningDateValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullOffsetDateTime));
      sq.with("version", entity.getVersionValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullInteger));
      return sq.execute().thenCompose(num -> {
        if (0 == num) {
          throw new IllegalArgumentException("No insert into");
        }
        return (verifier == null ? CompletableFuture.completedFuture(Optional.of(entity))
            : verifier.apply(entity).thenCompose(exists -> {
              if (exists) {
                return CompletableFuture.completedFuture(Optional.of(entity));
              } else {
                template.createSqlCommand("delete from \"place\" where \"uid\" = :uid")
                    .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
                return CompletableFuture.completedFuture(Optional.<Place>empty());
              }
            }));
      });
    }
  }
}
