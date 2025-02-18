package org.acme.features.market.color.infrastructure.repository;

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
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.gateway.ColorOrder;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestScoped
public class ColorRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @return
   */
  public long count(ColorFilter filter) {
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
  public Color create(Color entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public Color create(Color entity, Function<Color, Boolean> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Color delete(Color entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"color\" where \"uid\" = :uid");
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
  public Color enrich(ColorRef reference) {
    return reference instanceof Color ? (Color) reference
        : retrieve(reference.getUidValue(), Optional.empty())
            .orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Color: " + reference.getUidValue()));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public boolean exists(String uid, Optional<ColorFilter> filter) {
    return retrieve(uid, filter).isPresent();
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Slider<Color> list(ColorFilter filter, ColorCursor cursor) {
    return new ColorSlider(runList(filter, cursor), cursor.getLimit().orElse(0), this::runList,
        filter, cursor);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public Optional<Color> retrieve(String uid, Optional<ColorFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      ColorFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> ColorFilter.builder().uid(uid).build());
      SqlSchematicQuery<Color> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).one();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Color update(Color entity) {
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
  private <T> SqlSchematicQuery<T> filteredQuery(SqlTemplate template, ColorFilter filter) {
    SqlSchematicQuery<T> sq = template.createSqlSchematicQuery("color");
    sq.selectFields("color.uid", "color.name", "color.merchant", "color.version");
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
    if (!filter.getMerchants().isEmpty()) {
      sq.where("merchant", SqlOperator.IN, SqlListParameterValue.strings(filter.getMerchants()));
    }
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
  private Color runCreate(Color entity, Function<Color, Boolean> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"color\" ( \"uid\", \"name\", \"merchant\", \"version\") values ( :uid, :name, :merchant, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("merchant", entity.getMerchantReferenceValue().map(SqlParameterValue::of)
          .orElseGet(SqlParameterValue::ofNullString));
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
  private Iterator<Color> runList(ColorFilter filter, ColorCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Color> sq = filteredQuery(template, filter);
      PartialWhere offset = PartialWhere.empty();
      PartialWhere prev = PartialWhere.empty();
      if (null != cursor.getOrder()) {
        for (ColorOrder order : cursor.getOrder()) {
          if (order == ColorOrder.NAME_ASC) {
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
          if (order == ColorOrder.NAME_DESC) {
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
  private Color verified(Boolean exists, Color entity, SqlTemplate template) {
    if (exists) {
      return entity;
    } else {
      template.createSqlCommand("delete from \"color\" where \"uid\" = :uid")
          .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
      throw new NotFoundException("");
    }
  }
}
