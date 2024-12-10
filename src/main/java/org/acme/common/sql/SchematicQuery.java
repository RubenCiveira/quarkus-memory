package org.acme.common.sql;

import java.util.LinkedHashMap;
import java.util.Map;

public class SchematicQuery {
  private final String type;
  private final String table;
  private final AbstractSqlParametrized<?> parametrized;
  private final StringBuilder select = new StringBuilder();
  private final StringBuilder where = new StringBuilder();
  private final StringBuilder join = new StringBuilder();
  private final StringBuilder order = new StringBuilder();
  private final Map<String, String> set = new LinkedHashMap<>();

  public SchematicQuery(String type, String table, AbstractSqlParametrized<?> parametrized) {
    this.type = type;
    this.table = table;
    this.parametrized = parametrized;
  }

  public String buildQuery() {
    if (type.equals("SELECT")) {
      return "SELECT " + (select.length() > 0 ? select.substring(2) : " * ") + " FROM "
          + escape(table) + " " + (join.length() > 1 ? " JOIN " + join : "")
          + (where.length() > 1 ? " WHERE " + where.substring(5) : "")
          + (order.length() > 1 ? " ORDER BY " + order.substring(2) : "");
    } else if (type.equals("DELETE")) {
      return "DELETE FROM " + escape(table) + " "
          + (where.length() > 1 ? " WHERE " + where.substring(5) : "");
    } else if (type.equals("INSERT")) {
      StringBuilder theSet = new StringBuilder();
      StringBuilder theField = new StringBuilder();
      set.forEach((key, value) -> {
        theField.append(key);
        theSet.append(value);
      });
      return "INSERT INT " + escape(table) + " (" + theField + ") VALUES (" + theSet + ")";
    } else if (type.equals("UPDATE")) {
      StringBuilder theSet = new StringBuilder();
      set.forEach((key, value) -> theSet.append(key + " = " + value));
      return "UPDATE " + escape(table) + " SET (" + theSet + ") "
          + (where.length() > 1 ? " WHERE " + where.substring(5) : "");
    } else {
      return null;
    }
  }

  public void select(String... fields) {
    for (String field : fields) {
      this.select.append(", " + escape(field) + "");
    }
  }

  public void join(String table, String as, String currentOn, String remoteOn) {
    join.append(escape(table) + " as " + escape(as) + " on " + escape(currentOn) + " = "
        + escape(remoteOn));
  }

  public void set(String field, SqlParameterValue value) {
    String name = "_value_" + set.size();
    parametrized.with(name, value);
    set.put(escape(field), ":" + name);
  }

  public void where(String field, SqlOperator operator, SqlParameterValue value) {
    String name = "_field_" + where.length();
    where.append(" and " + escape(field) + " " + operator.value + " :" + name);
    parametrized.with(name, value);

  }

  public void orderAsc(String field) {
    order(field, "asc");
  }

  public void orderDesc(String field) {
    order(field, "desc");
  }

  private String escape(String name) {
    String[] parts = name.split("\\.");
    return "\"" + String.join("\".\"", parts) + "\"";
  }

  private void order(String field, String direction) {
    order.append(", \"" + field + "\" " + direction);
  }
}
