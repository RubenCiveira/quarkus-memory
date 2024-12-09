package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public abstract class AbstractSqlQuery<T, R extends AbstractSqlQuery<T,R>> extends SqlParametrized<R> {
  
  public AbstractSqlQuery(Connection connection) {
    super(connection);
  }

  public AbstractSqlQuery(DataSource source) {
    super(source);
  }
  
  public abstract SqlResult<T> query(SqlConverter<T> converter);
}
