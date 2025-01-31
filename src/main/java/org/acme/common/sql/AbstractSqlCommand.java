package org.acme.common.sql;

public abstract class AbstractSqlCommand<T extends AbstractSqlCommand<T>>
    extends AbstractSqlParametrized<T> {

  /* default */ AbstractSqlCommand(SqlTemplate template) {
    super(template);
  }

  public abstract Integer execute();
}
