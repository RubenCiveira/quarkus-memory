package org.acme.common.migration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class H2Dialect implements SQLDialect {

  @Override
  public String createLogTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (name VARCHAR(250), filename VARCHAR(250),md5sum VARCHAR(35),execution TIMESTAMP,error VARCHAR(250));";
  }

  @Override
  public String createLockTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (id INT PRIMARY KEY,locked BOOLEAN,granted TIMESTAMP);";
  }

  @Override
  public String insertLock(String name) {
    return "MERGE INTO " + name + " (id, locked, granted) KEY (id) VALUES (1, FALSE, NULL);";
  }

  @Override
  public String releaseLock(String name) {
    return "UPDATE " + name + " SET locked = FALSE, granted = NULL WHERE id = 1";
  }

  @Override
  public String markOkSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=NULL, execution=CURRENT_TIMESTAMP WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, NULL, ?, ?, CURRENT_TIMESTAMP)";
  }

  @Override
  public String markFailSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=?, execution=CURRENT_TIMESTAMP WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
  }

  @Override
  public String updateLock(String name) {
    return "UPDATE " + name + " SET locked = TRUE, granted = CURRENT_TIMESTAMP WHERE id = 1";
  }

  @Override
  public boolean interpretLocked(ResultSet rs) throws SQLException {
    return rs.getBoolean("locked");
  }
}
