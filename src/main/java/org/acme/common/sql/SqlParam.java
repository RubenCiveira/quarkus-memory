package org.acme.common.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    bind(position, stat, value);
  }
  public void bind(Integer position, PreparedStatement stat, Object localValue) throws SQLException {
    if( localValue instanceof Optional<?> op) {
      bind(position, stat, op.orElse(null));
    } else if (type == SqlType.INTEGER) {
     stat.setInt(position, (Integer)localValue);
    } else if ( type == SqlType.DATE ) {
      stat.setDate(position, null == localValue ? null : new Date( ((Date)localValue).getTime() ) );
    } else {
      stat.setString(position, null == localValue ? null : String.valueOf( localValue ) );
    }
  }
}


