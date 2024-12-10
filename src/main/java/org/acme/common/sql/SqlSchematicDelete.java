package org.acme.common.sql;

public final class SqlSchematicDelete extends AbstractSqlCommand<SqlSchematicDelete> {
  
  public SqlSchematicDelete(SqlTemplate template) {
    super(template);
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
