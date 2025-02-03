package org.acme.features.market.merchant.infrastructure.repository;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

import org.acme.common.algorithms.Slider;
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
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantOrder;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MerchantRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Merchant create(Merchant entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public Merchant create(Merchant entity, Function<Merchant, Boolean> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Merchant delete(Merchant entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"merchant\" where \"uid\" = :uid");
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
  public Merchant enrich(MerchantRef reference) {
    return reference instanceof Merchant ? (Merchant) reference
        : retrieve(reference.getUidValue(), Optional.empty())
            .orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Merchant: " + reference.getUidValue()));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public boolean exists(String uid, Optional<MerchantFilter> filter) {
    return retrieve(uid, filter).isPresent();
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Slider<Merchant> list(MerchantFilter filter, MerchantCursor cursor) {
    return new MerchantSlider(runList(filter, cursor), cursor.getLimit().orElse(0), this::runList,
        filter, cursor);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public Optional<Merchant> retrieve(String uid, Optional<MerchantFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      MerchantFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> MerchantFilter.builder().uid(uid).build());
      SqlSchematicQuery<Merchant> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).one();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Merchant update(Merchant entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"merchant\" set  \"name\" = :name, \"enabled\" = :enabled, \"key\" = :key, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("enabled", SqlParameterValue.of(entity.getEnabledValue()));
      sq.with("key", entity.getKeyValue().map(SqlParameterValue::of)
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
  private SqlConverter<Merchant> converter() {
    return (row) -> {
      try {
        return Optional.of(Merchant.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
            .enabledValue(row.getBoolean(3)).keyValue(row.getString(4)).versionValue(row.getInt(5))
            .build());
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
  private SqlSchematicQuery<Merchant> filteredQuery(SqlTemplate template, MerchantFilter filter) {
    SqlSchematicQuery<Merchant> sq = template.createSqlSchematicQuery("merchant");
    sq.select("merchant.uid", "merchant.name", "merchant.enabled", "merchant.key",
        "merchant.version");
    filter.getUid().ifPresent(uid -> sq.where("uid", SqlOperator.EQ, SqlParameterValue.of(uid)));
    if (!filter.getUids().isEmpty()) {
      sq.where("uid", SqlOperator.IN, SqlListParameterValue.strings(filter.getUids()));
    }
    filter.getSearch().ifPresent(
        search -> sq.where("name", SqlOperator.LIKE, SqlParameterValue.of("%" + search + "%")));
    filter.getEnabled()
        .ifPresent(enabled -> sq.where("enabled", SqlOperator.EQ, SqlParameterValue.of(enabled)));
    filter.getName()
        .ifPresent(name -> sq.where("name", SqlOperator.EQ, SqlParameterValue.of(name)));
    filter.getMerchantAccesible().ifPresent(merchantAccesible -> sq.where("uid", SqlOperator.EQ,
        SqlParameterValue.of(merchantAccesible)));
    return sq;
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  private Merchant runCreate(Merchant entity, Function<Merchant, Boolean> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"merchant\" ( \"uid\", \"name\", \"enabled\", \"key\", \"version\") values ( :uid, :name, :enabled, :key, :version)");
      sq.with("uid", SqlParameterValue.of(entity.getUidValue()));
      sq.with("name", SqlParameterValue.of(entity.getNameValue()));
      sq.with("enabled", SqlParameterValue.of(entity.getEnabledValue()));
      sq.with("key", entity.getKeyValue().map(SqlParameterValue::of)
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
  private Iterator<Merchant> runList(MerchantFilter filter, MerchantCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Merchant> sq = filteredQuery(template, filter);
      PartialWhere offset = PartialWhere.empty();
      PartialWhere prev = PartialWhere.empty();
      if (null != cursor.getOrder()) {
        for (MerchantOrder order : cursor.getOrder()) {
          if (order == MerchantOrder.NAME_ASC) {
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
          if (order == MerchantOrder.NAME_DESC) {
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
  private Merchant verified(Boolean exists, Merchant entity, SqlTemplate template) {
    if (exists) {
      return entity;
    } else {
      template.createSqlCommand("delete from \"merchant\" where \"uid\" = :uid")
          .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
      throw new NotFoundException("");
    }
  }
}
