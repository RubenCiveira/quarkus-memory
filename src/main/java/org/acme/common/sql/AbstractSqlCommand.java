package org.acme.common.sql;

public abstract class AbstractSqlCommand<T extends AbstractSqlCommand<T>>
    extends AbstractSqlParametrized<T> {

  public AbstractSqlCommand(SqlTemplate template) {
    super(template);
  }

  public abstract int execute();
}
