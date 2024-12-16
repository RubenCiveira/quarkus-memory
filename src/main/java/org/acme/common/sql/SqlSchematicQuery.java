package org.acme.common.sql;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.acme.common.sql.SchematicQuery.Partial;

public final class SqlSchematicQuery<T> extends AbstractSqlQuery<T, SqlSchematicQuery<T>> {
  private final SchematicQuery query;
  
  public static Partial or(Function<Partial, List<Partial>> p) {
    return Partial.or(p);
  }
  
  public static Partial and(Function<Partial, List<Partial>> p) {
    return Partial.and(p);
  }
  
  public static Partial not(Function<Partial, List<Partial>> p) {
    return Partial.not(p);
  }
  
  public SqlSchematicQuery(SqlTemplate template, String table) {
    super(template);
    this.query = new SchematicQuery("SELECT", table, this);
  }

  @Override
  public CompletableFuture<SqlResult<T>> query(SqlConverter<T> converter) {
    return executeQuery(this.query.buildQuery(), converter);
  }

  public SqlSchematicQuery<T> select(String... fields) {
    this.query.select(fields);
    return this;
  }

  public SqlSchematicQuery<T> orderAsc(String field) {
    this.query.orderAsc(field);
    return this;
  }

  public SqlSchematicQuery<T> orderDesc(String field) {
    this.query.orderDesc(field);
    return this;
  }

  public SqlSchematicQuery<T> where(String on, String string, SqlOperator gt, SqlParameterValue of) {
    this.query.where(on, string, gt, of);
    return this;
  }
  
  public SqlSchematicQuery<T> where(String string, SqlOperator gt, SqlParameterValue of) {
    this.query.where(string, gt, of);
    return this;
  }
  
  public SqlSchematicQuery<T> where(Partial partial) {
    this.query.where(partial);
    return this;
  }
}
