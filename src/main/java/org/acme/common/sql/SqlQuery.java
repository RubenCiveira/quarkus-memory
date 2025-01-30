package org.acme.common.sql;

public final class SqlQuery<T> extends AbstractSqlQuery<T, SqlQuery<T>> {
  private final String sql;

  /* default */ SqlQuery(SqlTemplate template, String sql) {
    super(template);
    this.sql = sql;
  }

  @Override
  public SqlQuery<T> with(String name, SqlParameterValue consumer) {
    super.with(name, consumer);
    return this;
  }

  @Override
  public SqlResult<T> query(SqlConverter<T> converter) {
    return executeQuery(sql, converter);
  }
}
