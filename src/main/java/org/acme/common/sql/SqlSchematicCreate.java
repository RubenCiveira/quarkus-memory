package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlSchematicCreate extends AbstractSqlCommand<SqlSchematicCreate> {

  public SqlSchematicCreate(Connection connection) {
    super(connection);
  }

  public SqlSchematicCreate(DataSource source) {
    super(source);
  }
  
  @Override
  public int execute() {
    // TODO Auto-generated method stub
    return 0;
  }
  
}
