package org.acme.common.sql;

import java.sql.SQLException;

public class NotEmptyChildsException extends UncheckedSqlException {

  private static final long serialVersionUID = 5234631234382806277L;

  public NotEmptyChildsException(SQLException ex) {
    super(ex);
  }

  public NotEmptyChildsException(String msg, SQLException ex) {
    super(msg, ex);
  }

}