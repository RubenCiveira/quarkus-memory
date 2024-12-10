package org.acme.common.sql;

public enum SqlOperator {
  EQ("="), GT(">"), LT("<"), GTEQ(">="), LTEQ("<="), IN("IN");
  
  /* default */ final String value;
  
  private SqlOperator(String value) {
    this.value = value;
  }
}