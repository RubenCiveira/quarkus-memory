package org.acme.common.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlConverter<R> {
  R convert(ResultSet t) throws SQLException;
}
