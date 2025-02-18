package org.acme.features.market.medal.infrastructure.repository;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.algorithms.Slider;
import org.acme.common.exception.ConstraintException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.infrastructure.sql.OptimistLockException;
import org.acme.common.infrastructure.sql.PartialWhere;
import org.acme.common.infrastructure.sql.SqlCommand;
import org.acme.common.infrastructure.sql.SqlConverter;
import org.acme.common.infrastructure.sql.SqlListParameterValue;
import org.acme.common.infrastructure.sql.SqlOperator;
import org.acme.common.infrastructure.sql.SqlParameterValue;
import org.acme.common.infrastructure.sql.SqlSchematicQuery;
import org.acme.common.infrastructure.sql.SqlTemplate;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.gateway.MedalOrder;
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
   * @param filter
   * @return
   */
  public long count(MedalFilter filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Long> sq = filteredQuery(template, filter);
      sq.select("count(uid) as uid");
      return sq.query(row -> {
        return Optional.of(row.getLong(1));
      }).one().orElse(0l);
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Medal create(Medal entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public Medal create(Medal entity, Function<Medal, Boolean> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Medal delete(Medal entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"medal\" where \"uid\" = :uid");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      int num = sq.execute();
      if (0 == num) {
        throw new IllegalArgumentException("No delete from");
      }
      return entity;
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param reference
   * @return
   */
  public Medal enrich(MedalRef reference) {
    return reference instanceof Medal ? (Medal) reference
        : retrieve(reference.getUidValue(), Optional.empty())
            .orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Medal: " + reference.getUidValue()));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public boolean exists(String uid, Optional<MedalFilter> filter) {
    return retrieve(uid, filter).isPresent();
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Slider<Medal> list(MedalFilter filter, MedalCursor cursor) {
    return new MedalSlider(runList(filter, cursor), cursor.getLimit().orElse(0), this::runList,
        filter, cursor);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public Optional<Medal> retrieve(String uid, Optional<MedalFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      MedalFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> MedalFilter.builder().uid(uid).build());
      SqlSchematicQuery<Medal> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).one();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Medal update(Medal entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"medal\" set  \"name\" = :name, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("version", entity.getVersionValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullInteger));
      int num = sq.execute();
      if (0 == num) {
        throw new OptimistLockException("No delete from");
      }
      return entity.withVersionValue(version + 1);
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
  private <T> SqlSchematicQuery<T> filteredQuery(SqlTemplate template, MedalFilter filter) {
    SqlSchematicQuery<T> sq = template.createSqlSchematicQuery("medal");
    sq.selectFields("medal.uid", "medal.name", "medal.version");
    filter.getUid().ifPresent(uid -> sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid)));
    if (!filter.getUids().isEmpty()) {
      sq.where("uid", SqlOperator.IN, SqlListParameterValue.strings(filter.getUids()));
    }
    filter.getSearch().ifPresent(
        search -> sq.where("name", SqlOperator.LIKE, SqlParameterValue.of("%" + search + "%")));
    filter.getName()
        .ifPresent(name -> sq.where("name", SqlOperator.EQ, SqlParameterValue.of(name)));
    return sq;
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  private Medal runCreate(Medal entity, Function<Medal, Boolean> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"medal\" ( \"uid\", \"name\", \"version\") values ( :uid, :name, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("version", entity.getVersionValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullInteger));
      int num = sq.execute();
      if (0 == num) {
        throw new IllegalArgumentException("No insert into");
      }
      return verifier == null ? entity : verified(verifier.apply(entity), entity, template);
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  private Iterator<Medal> runList(MedalFilter filter, MedalCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Medal> sq = filteredQuery(template, filter);
      PartialWhere offset = PartialWhere.empty();
      PartialWhere prev = PartialWhere.empty();
      if (null != cursor.getOrder()) {
        for (MedalOrder order : cursor.getOrder()) {
          if (order == MedalOrder.NAME_ASC) {
            sq.addOrderAsc("name");
            Optional<String> sinceName = cursor.getSinceName();
            if (sinceName.isPresent()) {
              String sinceNameValue = sinceName.get();
              offset = PartialWhere.or(offset, PartialWhere.and(prev, PartialWhere.where("name",
                  SqlOperator.GT, SqlParameterValue.of(sinceNameValue))));
              prev = PartialWhere.and(prev,
                  PartialWhere.where("name", SqlOperator.EQ, SqlParameterValue.of(sinceNameValue)));
            }
          }
          if (order == MedalOrder.NAME_DESC) {
            sq.addOrderDesc("name");
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
      return sq.query(converter()).limit(cursor.getLimit()).iterator();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param exists
   * @param entity
   * @param template
   * @return
   */
  private Medal verified(Boolean exists, Medal entity, SqlTemplate template) {
    if (exists) {
      return entity;
    } else {
      template.createSqlCommand("delete from \"medal\" where \"uid\" = :uid")
          .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
      throw new NotFoundException("");
    }
  }
}
