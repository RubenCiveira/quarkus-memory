/* @autogenerated */
package org.acme.common.infrastructure.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/* default */ class StaticSqlParameterValue implements SqlParameterValue {
  /* default */ static interface CustomAccepter {
    void accept(int index, PreparedStatement ps) throws SQLException;
  }

  private final String value;
  private final CustomAccepter accepter;

  public StaticSqlParameterValue(Object value, CustomAccepter accepter) {
    this.value = String.valueOf( value );
    this.accepter = accepter;
  }

  
  public StaticSqlParameterValue(String value, CustomAccepter accepter) {
    this.value = value;
    this.accepter = accepter;
  }

  @Override
  public String valueDescription() {
    return value;
  }

  @Override
  public void accept(int index, PreparedStatement ps) throws SQLException {
    accepter.accept(index, ps);
  }

}
