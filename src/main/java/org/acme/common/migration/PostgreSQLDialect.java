package org.acme.common.migration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgreSQLDialect implements SQLDialect {

  @Override
  public String createLogTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (name VARCHAR(250),filename VARCHAR(250),md5sum VARCHAR(35), execution TIMESTAMP, error VARCHAR(250) );";
  }

  @Override
  public String createLockTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (id INT PRIMARY KEY, locked BOOLEAN, granted TIMESTAMP);";
  }

  @Override
  public String insertLock(String name) {
    return "INSERT INTO " + name
        + " (id, locked, granted) VALUES (1, FALSE, NULL) ON CONFLICT DO NOTHING;";
  }

  @Override
  public String releaseLock(String name) {
    return "UPDATE " + name + " SET locked = FALSE, granted = NULL WHERE id = 1";
  }

  @Override
  public String markOkSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=NULL, execution=NOW() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, NULL, ?, ?, NOW())";
  }

  @Override
  public String markFailSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name + " SET md5sum=?, error=?, execution=NOW() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, ?, ?, ?, NOW())";
  }

  @Override
  public String updateLock(String name) {
    return "UPDATE " + name + " SET locked = TRUE, granted = NOW() WHERE id = 1";
  }

  @Override
  public boolean interpretLocked(ResultSet rs) throws SQLException {
    return rs.getBoolean("locked");
  }
}
