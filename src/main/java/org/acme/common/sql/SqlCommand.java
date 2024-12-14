package org.acme.common.sql;

import java.util.concurrent.CompletableFuture;

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
  public CompletableFuture<Integer> execute() {
    return executeUpdate(this.sql);
  }

}
