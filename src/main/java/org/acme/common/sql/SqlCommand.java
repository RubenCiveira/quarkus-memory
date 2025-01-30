package org.acme.common.sql;

import org.acme.common.reactive.Stream;

public final class SqlCommand extends AbstractSqlCommand<SqlCommand> {
  private final String sql;

  /* default */ SqlCommand(SqlTemplate template, String sql) {
    super(template);
    this.sql = sql;
  }

  @Override
  public SqlCommand with(String name, SqlParameterValue consumer) {
    super.with(name, consumer);
    return this;
  }

  @Override
  public Stream<Integer> execute() {
    return executeUpdate(this.sql).completed(this::close);
  }
}
