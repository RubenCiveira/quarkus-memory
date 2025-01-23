package org.acme.common.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartialWhere {
  public static PartialWhere or(PartialWhere... partials) {
    PartialWhere partial = new PartialWhere("or", "");
    for (PartialWhere part : partials) {
      if (!part.isEmpty()) {
        partial.params.add(new Object[] {part});
      }
    }
    return partial;
  }

  @SafeVarargs
  public static PartialWhere or(Optional<PartialWhere>... partials) {
    PartialWhere partial = new PartialWhere("or", "");
    for (Optional<PartialWhere> part : partials) {
      part.ifPresent(tpart -> {
        if (!part.isEmpty()) {
          partial.params.add(new Object[] {tpart});
        }
      });
    }
    return partial;
  }

  public static PartialWhere not(PartialWhere partial) {
    PartialWhere negated = new PartialWhere("and", "not");
    if (!partial.isEmpty()) {
      negated.params.add(new Object[] {partial});
    }
    return negated;
  }

  public static PartialWhere not(Optional<PartialWhere> partial) {
    PartialWhere negated = new PartialWhere("and", "not");
    partial.ifPresent(tpartial -> {
      if (!tpartial.isEmpty()) {
        negated.params.add(new Object[] {tpartial});
      }
    });
    return negated;
  }

  public static PartialWhere and(PartialWhere... partials) {
    PartialWhere partial = new PartialWhere("and", "");
    for (PartialWhere part : partials) {
      if (!part.isEmpty()) {
        partial.params.add(new Object[] {part});
      }
    }
    return partial;
  }

  @SafeVarargs
  public static PartialWhere and(Optional<PartialWhere>... partials) {
    PartialWhere partial = new PartialWhere("and", "");
    for (Optional<PartialWhere> part : partials) {
      part.ifPresent(tpart -> {
        if (!part.isEmpty()) {
          partial.params.add(new Object[] {tpart});
        }
      });
    }
    return partial;
  }

  public static PartialWhere where(String on, String field, SqlOperator operator,
      SqlParameterValue value) {
    PartialWhere partial = new PartialWhere("and", "");
    partial.params.add(new Object[] {on, field, operator, value});
    return partial;
  }

  public static PartialWhere where(String field, SqlOperator operator, SqlParameterValue value) {
    PartialWhere partial = new PartialWhere("and", "");
    partial.params.add(new Object[] {null, field, operator, value});
    return partial;
  }

  private final String join;
  private final String converter;
  List<Object[]> params = new ArrayList<>();

  private PartialWhere(String join, String converter) {
    this.join = join;
    this.converter = converter;
  }

  /* default */ boolean isEmpty() {
    return params.isEmpty();
  }

  /* default */ String append(int prefix, SchematicQuery parametrized) {
    List<String> wheres = new ArrayList<>();
    for (Object[] param : params) {
      if (param.length == 1) {
        PartialWhere p = (PartialWhere) param[0];
        if (!p.isEmpty()) {
          String where = p.append(++prefix, parametrized);
          prefix += where.length();
          wheres.add(where);
        }
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
    if (wheres.isEmpty()) {
      return "";
    } else if (wheres.size() == 1) {
      return (converter + " " + wheres.get(0)).trim();
    } else {
      return converter + "(" + String.join(" " + join + " ", wheres) + ")";
    }
  }

  public static PartialWhere empty() {
    PartialWhere partial = new PartialWhere("", "");
    return partial;
  }
}
