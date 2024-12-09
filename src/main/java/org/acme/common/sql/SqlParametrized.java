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
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;

public abstract class SqlParametrized<T extends SqlParametrized<T>> implements AutoCloseable {
  public static interface PsConsumer {
    void accept(int index, PreparedStatement ps) throws SQLException;
  }

  private final Map<String, PsConsumer> parameters = new LinkedHashMap<>();
  private final Map<String, Object> arrays = new LinkedHashMap<>();

  private final Connection connection;

  public SqlParametrized(Connection connection) {
    if (connection == null) {
      throw new IllegalArgumentException("Connection cannot be null");
    }
    this.connection = connection;
  }

  public SqlParametrized(DataSource source) {
    if (source == null) {
      throw new IllegalArgumentException("Connection cannot be null");
    }
    try {
      this.connection = source.getConnection();
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }

  /**
   * Inicia una transacción configurando auto-commit en false.
   */
  public void begin() {
    try {
      if (connection.getAutoCommit()) {
        connection.setAutoCommit(false);
      }
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }

  /**
   * Realiza un commit de la transacción actual.
   */
  public void commit() {
    try {
      if (!connection.getAutoCommit()) {
        connection.commit();
        connection.setAutoCommit(true); // Vuelve a habilitar auto-commit
      }
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }

  /**
   * Realiza un rollback de la transacción actual.
   */
  public void rollback() {
    try {
      if (!connection.getAutoCommit()) {
        connection.rollback();
        connection.setAutoCommit(true); // Vuelve a habilitar auto-commit
      }
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }

  /**
   * Cierra la conexión.
   */
  public void close() {
    try {
      if (!connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }
  
  @SuppressWarnings("unchecked")
  protected T with(String name, PsConsumer consumer) {
    parameters.put(name, consumer);
    return (T) this;
  }

  public T with(String name, String value) {
    return with(name, (index, ps) -> ps.setString(index, value));
  }

  @SuppressWarnings("unchecked")
  public T with(String name, String[] values) {
    parameters.put(name, (index, ps) -> {
      for (int i = 0; i < values.length; i++) {
        ps.setString(index + i, values[i]);
      }
    });
    arrays.put(name, values);
    return (T) this;
  }

  protected int executeUpdate(String sql) {
    try (PreparedStatement run = prepareStatement(sql)) {
      return run.executeUpdate();
    } catch (SQLException e) {
      throw new UncheckedSqlException(e);
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
        throw new UncheckedSqlException(ex);
      }
    };
    return new SqlResult<R>() {
      @Override
      public Optional<R> one() {
        return execute.apply(limitResults(sql, 1)).stream().findFirst();
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

  protected PreparedStatement prepareStatement(String sql) throws SQLException {
    Map<String, Integer> parameterIndexMap = new LinkedHashMap<>();
    PreparedStatement prepareStatement =
        connection.prepareStatement(formatSql(sql, parameterIndexMap));
    applyParameters(parameterIndexMap, prepareStatement);
    return prepareStatement;
  }

  private String formatSql(String sql, Map<String, Integer> parameterIndexMap) {
    try {
      sql = parseSql(escapeIdentifiers(sql), parameterIndexMap);
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
    // aquellos que sean listas: toca expandirlos.
    List<Integer> listSizes = new ArrayList<>();
    arrays.forEach((name, value) -> {
      if (value instanceof Object[] array) {
        listSizes.add(array.length);
        Integer position = parameterIndexMap.get(name);
        parameterIndexMap.forEach((key, index) -> {
          if (index > position) {
            parameterIndexMap.remove(key);
            parameterIndexMap.put(key, index + array.length - 1);
          }
        });
      }
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
        if (c == ':' && i + 1 < sql.length() && 
            (Character.isLetter(sql.charAt(i + 1)) || sql.charAt(i + 1) == '_')) {
            int j = i + 1;
            while (j < sql.length() && 
                   (Character.isLetterOrDigit(sql.charAt(j)) || sql.charAt(j) == '_')) {
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
      PreparedStatement preparedStatement) {
    parameters.forEach((key, value) -> {
      if( !parameterIndexMap.containsKey(key) ) {
        throw new IllegalArgumentException("No param " + key + " on the sentence");
      } else {
        try {
          value.accept(parameterIndexMap.get(key), preparedStatement);
        } catch (SQLException ex) {
          throw new UncheckedSqlException(ex);
        }
      }
    });
  }

  private String limitResults(String query, int size) {
    return query + " limit " + size;
  }
}

