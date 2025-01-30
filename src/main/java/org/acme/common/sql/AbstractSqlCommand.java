package org.acme.common.sql;

import org.acme.common.reactive.Stream;

public abstract class AbstractSqlCommand<T extends AbstractSqlCommand<T>>
    extends AbstractSqlParametrized<T> {

  /* default */ AbstractSqlCommand(SqlTemplate template) {
    super(template);
  }

  public abstract Stream<Integer> execute();
}
