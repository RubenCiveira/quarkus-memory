package org.acme.common.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSqlParametrized<T extends AbstractSqlParametrized<T>> {
  private final Map<String, SqlParameterValue> parameters = new LinkedHashMap<>();
  private final Map<String, Integer> arrays = new LinkedHashMap<>();

  private final Connection connection;

  public AbstractSqlParametrized(SqlTemplate template) {
    this.connection = template.currentConnection();
  }

  @SuppressWarnings("unchecked")
  protected T with(String name, SqlParameterValue consumer) {
    parameters.put(name, consumer);
    if (consumer instanceof SqlListParameterValue list) {
      arrays.put(name, list.size());
    }
    return (T) this;
  }

  protected int executeUpdate(String sql) {
    try (PreparedStatement run = prepareStatement(sql)) {
      return run.executeUpdate();
    } catch (SQLException e) {
      throw UncheckedSqlException.exception(connection, e);
    }
  }

  protected <R> SqlResult<R> executeQuery(String sql, SqlConverter<R> converter) {
    Function<String, List<R>> execute = (query) -> {
      try (PreparedStatement prepareStatement = prepareStatement(query);
          ResultSet executeQuery = prepareStatement.executeQuery()) {
        List<R> data = new ArrayList<>();
        while (executeQuery.next()) {
          data.add(converter.convert(executeQuery));
        }
        return data;
      } catch (SQLException ex) {
        throw UncheckedSqlException.exception(connection, ex);
      }
    };
    return new SqlResult<R>() {
      @Override
      public Optional<R> one() {
        return execute.apply(limitResults(sql, 1)).stream().findFirst();
      }

      @Override
      public List<R> limit(Optional<Integer> max) {
        return max.map(this::limit).orElseGet(this::all);
      }

      @Override
      public List<R> limit(int max) {
        return execute.apply(limitResults(sql, max));
      }

      @Override
      public List<R> all() {
        return execute.apply(sql);
      }
    };
  }

  private PreparedStatement prepareStatement(String sql) throws SQLException {
    Map<String, Integer> parameterIndexMap = new LinkedHashMap<>();
    PreparedStatement prepareStatement =
        connection.prepareStatement(formatSql(sql, parameterIndexMap));
    applyParameters(parameterIndexMap, prepareStatement);
    return prepareStatement;
  }

  private String formatSql(String sql, Map<String, Integer> parameterIndexMap) throws SQLException {
    sql = parseSql(escapeIdentifiers(sql), parameterIndexMap);
    // aquellos que sean listas: toca expandirlos.
    List<Integer> listSizes = new ArrayList<>();
    arrays.forEach((name, value) -> {
      if (!parameterIndexMap.containsKey(name)) {
        throw new IllegalArgumentException("No param " + name + " on the sentence");
      }
      listSizes.add(value);
      Integer position = parameterIndexMap.get(name);
      parameterIndexMap.forEach((key, index) -> {
        if (index > position) {
          // parameterIndexMap.remove(key);
          parameterIndexMap.replace(key, index + value - 1);
        }
      });
    });
    if (!listSizes.isEmpty()) {
      sql = replaceInPlaceholders(sql, listSizes);
    }
    return sql;
  }

  private String parseSql(String sql, Map<String, Integer> parameterIndexMap) {
    StringBuilder parsedSql = new StringBuilder();
    int index = 1;

    for (int i = 0; i < sql.length(); i++) {
      char c = sql.charAt(i);
      if (c == ':' && i + 1 < sql.length()
          && (Character.isLetter(sql.charAt(i + 1)) || sql.charAt(i + 1) == '_')) {
        int j = i + 1;
        while (j < sql.length()
            && (Character.isLetterOrDigit(sql.charAt(j)) || sql.charAt(j) == '_')) {
          j++;
        }
        String paramName = sql.substring(i + 1, j);
        if (!parameterIndexMap.containsKey(paramName)) {
          parameterIndexMap.put(paramName, index++);
        }
        parsedSql.append('?');
        i = j - 1;
      } else {
        parsedSql.append(c);
      }
    }
    return parsedSql.toString();
  }

  private String replaceInPlaceholders(String sql, List<Integer> paramSizes) {
    // Patrón para localizar IN (?)
    Pattern pattern = Pattern.compile("(?<!')\\bIN\\s*\\(\\s*\\?\\s*\\)(?!')");
    Matcher matcher = pattern.matcher(sql);

    StringBuffer result = new StringBuffer();
    int index = 0;

    while (matcher.find()) {
      if (index >= paramSizes.size()) {
        throw new IllegalArgumentException("Insufficient sizes provided for IN placeholders.");
      }

      // Generar el reemplazo dinámico con ? según el tamaño del parámetro
      String placeholders = String.join(", ", "?".repeat(paramSizes.get(index)).split(""));
      matcher.appendReplacement(result, "IN (" + placeholders + ")");
      index++;
    }
    matcher.appendTail(result);
    return result.toString();
  }

  private String escapeIdentifiers(String sql) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    String escapeChar = metaData.getIdentifierQuoteString();
    // Patrón para detectar literales y comillas dobles
    Pattern pattern = Pattern.compile("'([^']|'')*'|\"(.*?)\"");
    Matcher matcher = pattern.matcher(sql);
    StringBuilder result = new StringBuilder();
    int lastIndex = 0;

    while (matcher.find()) {
      // Agregar la parte antes del match
      result.append(sql, lastIndex, matcher.start());
      lastIndex = matcher.end();

      if (matcher.group(1) != null) { // Es un literal de cadena
        result.append(matcher.group());
      } else if (matcher.group(2) != null) { // Es un identificador entre comillas dobles
        String identifier = matcher.group(2);
        result.append(escapeChar).append(identifier).append(escapeChar);
      }
    }
    // Agregar el resto del texto
    result.append(sql.substring(lastIndex));
    return result.toString();
  }

  private void applyParameters(Map<String, Integer> parameterIndexMap,
      PreparedStatement preparedStatement) throws SQLException {
    for (Entry<String, SqlParameterValue> entry : parameters.entrySet()) {
      String key = entry.getKey();
      SqlParameterValue value = entry.getValue();
      if (!parameterIndexMap.containsKey(key)) {
        throw new IllegalArgumentException("No param " + key + " on the sentence");
      } else {
        value.accept(parameterIndexMap.get(key), preparedStatement);
      }
    }
  }

  private String limitResults(String query, int size) {
    return query + " limit " + size;
  }
}

