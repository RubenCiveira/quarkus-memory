package org.acme.common.sql;

public final class SqlCommand extends AbstractSqlCommand<SqlCommand> {
  private final String sql;

  public SqlCommand(SqlTemplate template, String sql) {
    super(template);
    this.sql = sql;
  }

  @Override
  public SqlCommand with(String name, SqlParameterValue consumer) {
    super.with(name, consumer);
    return this;
  }

  @Override
  public int execute() {
    return executeUpdate(this.sql);
  }

}
