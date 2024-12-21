package org.acme.common.sql;

import java.sql.SQLException;
import java.util.Optional;

@FunctionalInterface
public interface SqlConverter<R> {
  Optional<R> convert(SqlResultSet t) throws SQLException;
}
