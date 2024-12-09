package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlSchematicDelete extends AbstractSqlCommand<SqlSchematicDelete> {
  
  public SqlSchematicDelete(Connection connection) {
    super(connection);
  }

  public SqlSchematicDelete(DataSource source) {
    super(source);
  }

  public SqlSchematicDelete where(String column, Object value) {
    return this;
  }
  
  @Override
  public int execute() {
    // TODO Auto-generated method stub
    return 0;
  }
}
