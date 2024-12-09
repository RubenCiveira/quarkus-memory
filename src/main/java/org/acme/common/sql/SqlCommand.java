package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlCommand extends AbstractSqlCommand<SqlCommand> {
  private final String sql;

  public SqlCommand(Connection connection, String sql) {
    super(connection);
    this.sql = sql;
  }

  public SqlCommand(DataSource source, String sql) {
    super(source);
    this.sql = sql;
  }

  @Override
  public int execute() {
    return executeUpdate(this.sql);
  }

}
