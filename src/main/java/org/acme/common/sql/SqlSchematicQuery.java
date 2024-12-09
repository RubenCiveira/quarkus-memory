package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlSchematicQuery<T> extends AbstractSqlQuery<T, SqlSchematicQuery<T>> {
  private final SchematicQuery query;
  
  public SqlSchematicQuery(Connection connection, String table) {
    super(connection);
    this.query = new SchematicQuery("SELECT",  table, this);
  }

  public SqlSchematicQuery(DataSource source, String table) {
    super(source);
    this.query = new SchematicQuery("SELECT",  table, this);
  }

  @Override
  public SqlResult<T> query(SqlConverter<T> converter) {
    return executeQuery(this.query.buildQuery(), converter);
  }
  
  public SqlSchematicQuery<T> select(String... fields) {
    this.query.select(fields);
    return this;
  }
  
  public SqlSchematicQuery<T> where(String field, SqlCondition value) {
    this.query.where(field, value);
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
}
