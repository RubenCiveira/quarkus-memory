package org.acme.common.sql;

public abstract class AbstractSqlQuery<T, R extends AbstractSqlQuery<T,R>> extends SqlParametrized<R> {
  
  public AbstractSqlQuery(SqlTemplate template) {
    super(template);
  }
  
  public abstract SqlResult<T> query(SqlConverter<T> converter);
}
