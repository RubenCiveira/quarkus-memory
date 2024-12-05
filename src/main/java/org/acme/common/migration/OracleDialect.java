package org.acme.common.migration;

public class OracleDialect implements SQLDialect {

  @Override
  public String createLogTable(String name) {
    return "BEGIN EXECUTE IMMEDIATE 'CREATE TABLE " + name
        + " (name VARCHAR2(250),filename VARCHAR2(250),md5sum VARCHAR2(35), execution TIMESTAMP,error VARCHAR2(250))'; EXCEPTION WHEN OTHERS THEN IF SQLCODE = -955 THEN NULL; ELSE RAISE; END IF; END;";
  }

  @Override
  public String createLockTable(String name) {
    return "BEGIN EXECUTE IMMEDIATE ' CREATE TABLE " + name
        + " ( id NUMBER PRIMARY KEY, locked NUMBER(1), granted TIMESTAMP ) '; EXCEPTION WHEN OTHERS THEN IF SQLCODE = -955 THEN NULL; ELSE RAISE; END IF; END;";
  }

  @Override
  public String insertLock(String name) {
    return "INSERT INTO " + name + " (id, locked, granted) VALUES (1, 0, NULL);";
  }

  @Override
  public String markOkSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=NULL, execution=SYSDATE WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, NULL, ?, ?, SYSDATE)";
  }

  @Override
  public String markFailSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name + " SET md5sum=?, error=?, execution=SYSDATE WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, ?, ?, ?, SYSDATE)";
  }

  @Override
  public String updateLock(String name) {
    return "UPDATE " + name + " SET locked = 1, granted = SYSDATE WHERE id = 1";
  }

}