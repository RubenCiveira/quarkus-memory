package org.acme.common.data.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlConverter<R> {
  R convert(ResultSet t) throws SQLException;
}
