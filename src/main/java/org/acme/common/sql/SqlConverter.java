package org.acme.common.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@FunctionalInterface
public interface SqlConverter<R> {
  Optional<R> convert(ResultSet t) throws SQLException;
}
