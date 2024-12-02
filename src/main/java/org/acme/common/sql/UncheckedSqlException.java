package org.acme.common.sql;

import java.sql.SQLException;

public class UncheckedSqlException extends RuntimeException {
  private static final long serialVersionUID = 4262062984330839677L;

  public UncheckedSqlException(SQLException ex) {
    super(ex);
  }

  public UncheckedSqlException(String msg, SQLException ex) {
    super(msg, ex);
  }

}
