package org.acme.common.data.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public record SqlParam(String name, Object value, SqlType type) {

  public Object[] asArray() {
    Object[] array;
    if( value instanceof List<?> l) {
      array = l.toArray();
    } else if( value instanceof Object[] arr) {
      array = arr;
    } else {
      array = null;
    }
    return array;
  }
  
  public void bind(Integer position, PreparedStatement stat) throws SQLException {
    stat.setString(position, null == value ? null : String.valueOf( value ) );
  }
}
