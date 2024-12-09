package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public abstract class AbstractSqlCommand<T extends AbstractSqlCommand<T>> extends SqlParametrized<T> {
  
  public AbstractSqlCommand(Connection connection) {
    super(connection);
  }

  public AbstractSqlCommand(DataSource source) {
    super(source);
  }

  public abstract int execute();
}
