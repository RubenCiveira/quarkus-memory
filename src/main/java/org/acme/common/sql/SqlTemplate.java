package org.acme.common.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

public class SqlTemplate implements AutoCloseable {
  private final Connection connection;

  // Constructor que recibe la conexión
  public SqlTemplate(Connection connection) {
    if (connection == null) {
      throw new IllegalArgumentException("Connection cannot be null");
    }
    this.connection = connection;
  }

  // Constructor que recibe la conexión
  public SqlTemplate(DataSource source) {
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

  public String lastInsertedId() {
    return null;
  }

  public int execute(String sql, SqlParam... params) {
    Map<String, Integer> parameterIndexMap = new HashMap<>();
    String formatSql = formatSql(sql, params, parameterIndexMap);
    try (PreparedStatement prepareStatement = connection.prepareStatement(formatSql)) {
      for (SqlParam param : params) {
        Integer position = parameterIndexMap.get(param.name());
        param.bind(position, prepareStatement);
      }
      return prepareStatement.executeUpdate();
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }

  public <T> SqlResult<T> query(String sql, SqlConverter<T> converter, SqlParam... params) {
    Map<String, Integer> parameterIndexMap = new HashMap<>();
    String formatSql = formatSql(sql, params, parameterIndexMap);
    Function<String, List<T>> execute = (query) -> {
      try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
        for (SqlParam param : params) {
          Integer position = parameterIndexMap.get(param.name());
          param.bind(position, prepareStatement);
        }
        try (ResultSet executeQuery = prepareStatement.executeQuery()) {
          List<T> data = new ArrayList<>();
          while (executeQuery.next()) {
            data.add(converter.convert(executeQuery));
          }
          return data;
        }
      } catch (SQLException ex) {
        throw new UncheckedSqlException(ex);
      }
    };
    return new SqlResult<T>() {
      @Override
      public Optional<T> one() {
        return execute.apply(limitResults(formatSql, 1)).stream().findFirst();
      }

      @Override
      public List<T> limit(int max) {
        return execute.apply(limitResults(formatSql, max));
      }

      @Override
      public List<T> all() {
        return execute.apply(formatSql);
      }
    };
  }

  private String formatSql(String sql, SqlParam[] params, Map<String, Integer> parameterIndexMap) {
    try {
      sql = parseSql(escapeIdentifiers(sql), parameterIndexMap);
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
    // aquellos que sean listas: toca expandirlos.
    List<Integer> listSizes = new ArrayList<>();
    for (SqlParam param : params) {
      Object[] array = param.asArray();
      if (null != array) {
        listSizes.add(array.length);
        Integer position = parameterIndexMap.get(param.name());
        parameterIndexMap.forEach((key, value) -> {
          if (value > position) {
            parameterIndexMap.remove(key);
            parameterIndexMap.put(key, value + array.length - 1);
          }
        });
      }
    }
    if (!listSizes.isEmpty()) {
      sql = replaceInPlaceholders(sql, listSizes);
    }
    return sql;
  }

  // select * from tabla where id in ( :id ) or name in ( :name )

  // Convertimos los named params por su valor.
  private String parseSql(String sql, Map<String, Integer> parameterIndexMap) {
    StringBuilder parsedSql = new StringBuilder();
    int index = 1;

    for (int i = 0; i < sql.length(); i++) {
      char c = sql.charAt(i);
      if (c == ':' && i + 1 < sql.length() && Character.isLetter(sql.charAt(i + 1))) {
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

  private String limitResults(String query, int size) {
    return query + " limit " + size;
  }
}
