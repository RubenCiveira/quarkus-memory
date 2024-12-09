package org.acme.common.sql;

import java.sql.Connection;
import javax.sql.DataSource;

public final class SqlSchematicUpdate extends AbstractSqlCommand<SqlSchematicUpdate> {

  public SqlSchematicUpdate(Connection connection) {
    super(connection);
  }

  public SqlSchematicUpdate(DataSource source) {
    super(source);
  }

  @Override
  public int execute() {
    // TODO Auto-generated method stub
    return 0;
  }
}
