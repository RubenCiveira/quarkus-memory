package org.acme.common.sql;

public class OptimistLockException extends RuntimeException {

  private static final long serialVersionUID = 8518822091315061249L;

  public OptimistLockException() {
    super();
  }

  public OptimistLockException(String msg) {
    super(msg);
  }
}