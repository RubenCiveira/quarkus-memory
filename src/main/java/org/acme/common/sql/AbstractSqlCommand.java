package org.acme.common.sql;

import java.util.concurrent.CompletionStage;

public abstract class AbstractSqlCommand<T extends AbstractSqlCommand<T>>
    extends AbstractSqlParametrized<T> {

  public AbstractSqlCommand(SqlTemplate template) {
    super(template);
  }

  public abstract CompletionStage<Integer> execute();
}
