package org.acme.common.sql;

public final class SqlCommand extends AbstractSqlCommand<SqlCommand> {
  private final String sql;

  public SqlCommand(SqlTemplate template, String sql) {
    super(template);
    this.sql = sql;
  }

  @Override
  public int execute() {
    return executeUpdate(this.sql);
  }

}
