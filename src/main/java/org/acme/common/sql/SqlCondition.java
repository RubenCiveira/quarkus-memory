package org.acme.common.sql;

import org.acme.common.sql.SqlParametrized.PsConsumer;

public class SqlCondition {
  public static enum Operator {
    EQ("="), GT(">"), LT("<"), GTEQ(">="), LTEQ("<=");
    
    /* default */ final String value;
    
    private Operator(String value) {
      this.value = value;
    }
  }
  
  public static SqlCondition of(Operator op, String value) {
    return new SqlCondition(op.value, (index, ps) -> ps.setString(index, value));
  }
  public static SqlCondition of(Operator op, long value) {
    return new SqlCondition(op.value, (index, ps) -> ps.setLong(index, value));
  }
  
  private final String operator;
  private final PsConsumer consumer;
  
  private SqlCondition(String operator, PsConsumer consumer) {
    this.operator = operator;
    this.consumer = consumer;
  }
  
  /* default */ String getOperator() {
    return operator;
  }
  
  /* default */ PsConsumer getConsumer() {
    return consumer;
  }
}
