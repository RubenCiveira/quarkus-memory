package org.acme.common.sql;

import java.sql.SQLException;

public class NotUniqueException extends UncheckedSqlException {

  private static final long serialVersionUID = 8518822091315061249L;

  public NotUniqueException(SQLException ex) {
    super(ex);
  }

  public NotUniqueException(String msg, SQLException ex) {
    super(msg, ex);
  }
}
