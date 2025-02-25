/* @autogenerated */
package org.acme.common.infrastructure.sql;

public enum SqlOperator {
  EQ("=", null), GT(">", null), LT("<", null), GTEQ(">=", null), LTEQ("<=", null), IN("IN",
      ""), LIKE("LIKE", null);

  /* default */ final String value;
  /* default */ final String method;

  private SqlOperator(String value, String method) {
    this.value = value;
    this.method = method;
  }

  public String format(String param) {
    return " " + (method == null ? param : method + "(" + param + ")");
  }
}
