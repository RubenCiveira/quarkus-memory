package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlQuery<T> extends AbstractSqlQuery<T, SqlQuery<T>> {
  private final String sql;

  public SqlQuery(Connection connection, String sql) {
    super(connection);
    this.sql = sql;
  }

  public SqlQuery(DataSource source, String sql) {
    super(source);
    this.sql = sql;
  }

  @Override
  public SqlResult<T> query(SqlConverter<T> converter) {
    return executeQuery(sql, converter);
  }
}
