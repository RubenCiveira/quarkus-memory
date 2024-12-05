package org.acme.common.migration;

public class SQLServerDialect implements SQLDialect {

  @Override
  public String createLogTable(String name) {
    return "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='" + name
        + "' AND xtype='U')CREATE TABLE " + name
        + " (name NVARCHAR(250),filename NVARCHAR(250),md5sum NVARCHAR(35),execution DATETIME,error NVARCHAR(250));";
  }

  @Override
  public String createLockTable(String name) {
    return "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='" + name
        + "' AND xtype='U')CREATE TABLE " + name
        + " (id INT PRIMARY KEY,locked BIT,granted DATETIME);";
  }

  @Override
  public String insertLock(String name) {
    return "IF NOT EXISTS (SELECT * FROM " + name + " WHERE id = 1) INSERT INTO " + name
        + " (id, locked, granted) VALUES (1, 0, NULL);";
  }

  @Override
  public String markOkSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=NULL, execution=GETDATE() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, NULL, ?, ?, GETDATE())";
  }

  @Override
  public String markFailSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=?, execution=GETDATE() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, ?, ?, ?, GETDATE())";
  }

  @Override
  public String updateLock(String name) {
    return "UPDATE " + name + " SET locked = 1, granted = GETDATE() WHERE id = 1";
  }

}