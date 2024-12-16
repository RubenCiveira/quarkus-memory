package org.acme.common.sql;

import java.util.concurrent.CompletionStage;

public abstract class AbstractSqlQuery<T, R extends AbstractSqlQuery<T, R>>
    extends AbstractSqlParametrized<R> {

  public AbstractSqlQuery(SqlTemplate template) {
    super(template);
  }

  public abstract CompletionStage<SqlResult<T>> query(SqlConverter<T> converter);
}
