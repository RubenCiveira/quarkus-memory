package org.acme.common.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class GenericSqlListParameterValues<T> implements SqlListParameterValue {
  public static interface GenericSqlListParameterValue<T> {
    public abstract void accept(int index, T value, PreparedStatement ps) throws SQLException;
  }

  private final T[] data;
  private final GenericSqlListParameterValues.GenericSqlListParameterValue<T> value;

  public GenericSqlListParameterValues(T[] data, GenericSqlListParameterValues.GenericSqlListParameterValue<T> value) {
    this.data = data;
    this.value = value;
  }

  public void accept(int index, PreparedStatement ps) throws SQLException {
    for (int i = 0; i < data.length; i++) {
      this.value.accept(index + i, data[i], ps);
    }
  }

  public int size() {
    return data.length;
  }

}