package org.acme.common.sql;

import java.sql.SQLException;

public class NotExistentReferenceException extends UncheckedSqlException {

  private static final long serialVersionUID = 7881855707464559727L;

  public NotExistentReferenceException(SQLException ex) {
    super(ex);
  }

  public NotExistentReferenceException(String msg, SQLException ex) {
    super(msg, ex);
  }

  
}
