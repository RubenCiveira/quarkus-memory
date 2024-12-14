package org.acme.common.sql;

import java.util.concurrent.CompletableFuture;

public final class SqlQuery<T> extends AbstractSqlQuery<T, SqlQuery<T>> {
  private final String sql;

  public SqlQuery(SqlTemplate template, String sql) {
    super(template);
    this.sql = sql;
  }

  @Override
  public SqlQuery<T> with(String name, SqlParameterValue consumer) {
    super.with(name, consumer);
    return this;
  }

  @Override
  public CompletableFuture<SqlResult<T>> query(SqlConverter<T> converter) {
    return executeQuery(sql, converter);
  }
}
