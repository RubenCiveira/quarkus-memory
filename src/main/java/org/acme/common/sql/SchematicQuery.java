package org.acme.common.sql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SchematicQuery {
  public static class Partial {
    public static Partial or(Function<Partial, List<Partial>> p) {
      Partial partial = new Partial("or", "");
      p.apply(partial);
      return partial;
    }

    public static Partial and(Function<Partial, List<Partial>> p) {
      Partial partial = new Partial("and", "");
      p.apply(partial);
      return partial;
    }

    public static Partial not(Function<Partial, List<Partial>> p) {
      Partial partial = new Partial("and", "not");
      p.apply(partial);
      return partial;
    }

    private final String join;
    private final String converter;
    List<Object[]> params = new ArrayList<>();


    private Partial(String join, String converter) {
      this.join = join;
      this.converter = converter;
    }

    public Partial where(String on, String field, SqlOperator operator, SqlParameterValue value) {
      params.add(new Object[] {on, field, operator, value});
      return this;
    }

    public Partial where(String field, SqlOperator operator, SqlParameterValue value) {
      this.where(null, field, operator, value);
      return this;
    }

    public Partial where(Partial partial) {
      params.add(new Object[] {partial});
      return this;
    }

    public String append(int prefix, SchematicQuery parametrized) {
      List<String> wheres = new ArrayList<>();
      for (Object[] param : params) {
        if (param.length == 1) {
          String where = ((Partial) param[0]).append(++prefix, parametrized);
          prefix += where.length();
          wheres.add(where);
        } else {
          String on = (String) param[0];
          String field = (String) param[1];
          SqlOperator operator = (SqlOperator) param[2];
          SqlParameterValue value = (SqlParameterValue) param[3];

          String name = "_field_" + (++prefix);
          parametrized.parametrized.with(name, value);
          wheres.add((null == on ? "" : parametrized.escape(on) + ".") + parametrized.escape(field)
              + " " + operator.value + " :" + name);
        }
      }
      return " " + converter + " ( " + String.join(" " + join + " ", wheres) + " )";
    }
  }


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

  public void where(Partial partial) {
    where.append(" and " + partial.append(where.length(), this));
  }

  public void where(String on, String field, SqlOperator operator, SqlParameterValue value) {
    String name = "_field_" + where.length();
    where.append(" and " + (null == on ? "" : escape(on) + ".") + escape(field) + " "
        + operator.value + " :" + name);
    parametrized.with(name, value);
  }

  public void where(String field, SqlOperator operator, SqlParameterValue value) {
    where(null, field, operator, value);
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
