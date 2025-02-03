package org.acme.features.market.fruit.infrastructure.repository;

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
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitOrder;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FruitRepository {

  /**
   * @autogenerated RepositoryJdbcGenerator
   */
  private final DataSource datasource;

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Fruit create(Fruit entity) {
    return runCreate(entity, null);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @param verifier
   * @return
   */
  public Fruit create(Fruit entity, Function<Fruit, Boolean> verifier) {
    return runCreate(entity, verifier);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Fruit delete(Fruit entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand("delete from \"fruit\" where \"uid\" = :uid");
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
  public Fruit enrich(FruitRef reference) {
    return reference instanceof Fruit ? (Fruit) reference
        : retrieve(reference.getUidValue(), Optional.empty())
            .orElseThrow(() -> new NotFoundException(
                "Trying to enrich inexistent Fruit: " + reference.getUidValue()));
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public boolean exists(String uid, Optional<FruitFilter> filter) {
    return retrieve(uid, filter).isPresent();
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param filter
   * @param cursor
   * @return
   */
  public Slider<Fruit> list(FruitFilter filter, FruitCursor cursor) {
    return new FruitSlider(runList(filter, cursor), cursor.getLimit().orElse(0), this::runList,
        filter, cursor);
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param uid
   * @param filter
   * @return
   */
  public Optional<Fruit> retrieve(String uid, Optional<FruitFilter> filter) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      FruitFilter readyFilter = filter.map(val -> val.withUid(uid))
          .orElseGet(() -> FruitFilter.builder().uid(uid).build());
      SqlSchematicQuery<Fruit> sq = filteredQuery(template, readyFilter);
      return sq.query(converter()).one();
    }
  }

  /**
   * @autogenerated RepositoryJdbcGenerator
   * @param entity
   * @return
   */
  public Fruit update(Fruit entity) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      int version = entity.getVersionValue().orElse(0);
      SqlCommand sq = template.createSqlCommand(
          "update \"fruit\" set  \"name\" = :name, \"version\" = \"version\" + 1 where \"uid\" = :uid and \"version\" = :version");
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
  private SqlConverter<Fruit> converter() {
    return (row) -> {
      try {
        return Optional.of(Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
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
  private SqlSchematicQuery<Fruit> filteredQuery(SqlTemplate template, FruitFilter filter) {
    SqlSchematicQuery<Fruit> sq = template.createSqlSchematicQuery("fruit");
    sq.select("fruit.uid", "fruit.name", "fruit.version");
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
  private Fruit runCreate(Fruit entity, Function<Fruit, Boolean> verifier) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlCommand sq = template.createSqlCommand(
          "insert into \"fruit\" ( \"uid\", \"name\", \"version\") values ( :uid, :name, :version)");
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
  private Iterator<Fruit> runList(FruitFilter filter, FruitCursor cursor) {
    try (SqlTemplate template = new SqlTemplate(datasource)) {
      SqlSchematicQuery<Fruit> sq = filteredQuery(template, filter);
      PartialWhere offset = PartialWhere.empty();
      PartialWhere prev = PartialWhere.empty();
      if (null != cursor.getOrder()) {
        for (FruitOrder order : cursor.getOrder()) {
          if (order == FruitOrder.NAME_ASC) {
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
          if (order == FruitOrder.NAME_DESC) {
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

  private Fruit verified(Boolean exists, Fruit entity, SqlTemplate template) {
    if (exists) {
      System.out.println("SI CUMPLE EL FILTRO: CONSERVAMOS");
      return entity;
    } else {
      System.out.println("NO CUMPLE EL FILTRO: borramos ");
      template.createSqlCommand("delete from \"fruit\" where \"uid\" = :uid")
          .with("uid", SqlParameterValue.of(entity.getUidValue())).execute();
      throw new NotFoundException("");
    }
  }
}
