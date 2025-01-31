/* @autogenerated */
package org.acme.common.migration;

public class MySQLDialect implements SQLDialect {

  @Override
  public String createLogTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (name VARCHAR(250), filename VARCHAR(250), md5sum VARCHAR(35), execution DATETIME,error VARCHAR(250) );";
  }

  @Override
  public String createLockTable(String name) {
    return "CREATE TABLE IF NOT EXISTS " + name
        + " (id INT PRIMARY KEY, locked TINYINT(1),granted DATETIME);";
  }

  @Override
  public String insertLock(String name) {
    return "INSERT IGNORE INTO " + name + " (id, locked, granted) VALUES (1, 0, NULL);";
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

}
