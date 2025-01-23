package org.acme.common.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public abstract class AbstractSqlParametrized<T extends AbstractSqlParametrized<T>> {
  private static class ChildRequest<R> {
    public final int batch;
    public final String name;
    // El campo del padre que extramos para la query
    public final String bind;
    // El campo del hijo que apunta al padre para asociarlo
    public final String ref;
    public final String query;
    public final SqlConverter<R> converter;
    // Calculated
    public final List<String> params = new ArrayList<>();
    // Calculated
    public final Map<String, CompletableFuture<List<R>>> futures = new LinkedHashMap<>();
    public final Map<String, CompletableFuture<List<R>>> childData = new LinkedHashMap<>();


    public ChildRequest(int batch, String name, String bind, String ref, String query,
        SqlConverter<R> converter) {
      this.batch = batch;
      this.name = name;
      this.bind = bind;
      this.ref = ref;
      this.query = query;
      this.converter = converter;
    }
  }

  private final Map<String, SqlParameterValue> parameters = new LinkedHashMap<>();
  private final Map<String, Integer> arrays = new LinkedHashMap<>();
  private final List<ChildRequest<?>> childs = new ArrayList<>();

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

  protected CompletionStage<Integer> executeUpdate(String sql) {
    try (PreparedStatement run = prepareStatement(sql)) {
      return CompletableFuture.completedFuture(run.executeUpdate());
    } catch (SQLException e) {
      throw UncheckedSqlException.exception(connection, e);
    }
  }

  // consulta hija
  protected <S> void child(int batch, String name, String sql, String parentBind, String childRef,
      SqlConverter<S> converter) {
    // select * from childs where bind in (?)
    childs.add(new ChildRequest<>(batch, name, parentBind, childRef, sql, converter));
  }

  protected <R> CompletionStage<SqlResult<R>> executeQuery(String sql, SqlConverter<R> converter) {
    @SuppressWarnings({"unchecked", "rawtypes"})
    Function<String, List<R>> execute = (query) -> {
      for (ChildRequest child : childs) {
        child.futures.clear();
        child.childData.clear();
      }
      System.err.println(getClass() + ": execute Query" + sql);
      System.err.println("\t" + parameters);
      try (PreparedStatement prepareStatement = prepareStatement(query);
          ResultSet executeQuery = prepareStatement.executeQuery()) {
        List<R> data = new ArrayList<>();
        // Si tengo listas hijas => tengo que ir por ellas.
        while (executeQuery.next()) {
          // foreach child
          Map<String, CompletionStage<List<?>>> childData = new HashMap<>();
          for (ChildRequest child : childs) {
            String of = executeQuery.getString(child.bind);
            child.params.add(of);
            CompletionStage<List<?>> future = new CompletableFuture<>();
            child.futures.put(of, future);
            child.childData.put(of, new ArrayList<>());
            childData.put(child.name, future);
          }
          SqlResultSet row = SqlResultSet.builder().set(executeQuery).childs(childData).build();
          converter.convert(row).ifPresent(data::add);
        }
        for (ChildRequest child : childs) {
          List<List<String>> partitionList = partitionList(child.params, child.batch);
          for (List<String> list : partitionList) {
            resolveChilds(child, list);
          }
        }
        return data;
      } catch (SQLException ex) {
        throw UncheckedSqlException.exception(connection, ex);
      }
    };
    return CompletableFuture.completedFuture(new SqlResult<R>() {
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
    });
  }

  private List<List<String>> partitionList(List<String> list, int windowSize) {
    return IntStream.range(0, (list.size() + windowSize - 1) / windowSize)
        .mapToObj(i -> list.subList(i * windowSize, Math.min((i + 1) * windowSize, list.size())))
        .toList();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void resolveChilds(ChildRequest child, List<String> offset) throws SQLException {
    Map<String, Integer> childParams = new LinkedHashMap<>();
    childParams.put(child.ref, 1);
    String theSql = formatSql(child.query, childParams, Map.of(child.ref, offset.size()));
    try (PreparedStatement childps = connection.prepareStatement(theSql)) {
      // Replace childs params
      System.out.println("La query es " + theSql);
      applyParameters(childParams, childps,
          Map.of(child.ref, SqlListParameterValue.of((String[]) offset.toArray(new String[0]))));
      try (ResultSet childRes = childps.executeQuery()) {
        while (childRes.next()) {
          Optional<Object> ref =
              child.converter.convert(SqlResultSet.builder().set(childRes).build());
          if (ref.isPresent()) {
            String parentRef = childRes.getString(child.ref);
            System.out.println("El child está en " + parentRef);
            ((List) child.childData.get(parentRef)).add(ref.get());
          }
        }
      }
      System.out.println("El child está en " + child.childData);
      offset.forEach(key -> {
        ((CompletableFuture) child.futures.get(key)).complete(child.childData.get(key));
      });
    }
  }

  private PreparedStatement prepareStatement(String sql) throws SQLException {
    Map<String, Integer> parameterIndexMap = new LinkedHashMap<>();
    PreparedStatement prepareStatement =
        connection.prepareStatement(formatSql(sql, parameterIndexMap));
    applyParameters(parameterIndexMap, prepareStatement);
    return prepareStatement;
  }

  private String formatSql(String sql, Map<String, Integer> parameterIndexMap) throws SQLException {
    return formatSql(sql, parameterIndexMap, arrays);

  }

  private String formatSql(String sql, Map<String, Integer> parameterIndexMap,
      Map<String, Integer> larrays) throws SQLException {
    sql = parseSql(escapeIdentifiers(sql), parameterIndexMap);
    // aquellos que sean listas: toca expandirlos.
    List<Integer> listSizes = new ArrayList<>();
    larrays.forEach((name, value) -> {
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
    Pattern pattern = Pattern.compile("(?<!')\\b[Ii][Nn]\\s*\\(\\s*\\?\\s*\\)(?!')");
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

      // matcher.group(1) != null => Es un literal de cadena
      // matcher.group(2) != null => Es un identificador entre comillas dobles
      if (matcher.group(1) != null) {
        result.append(matcher.group());
      } else {
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
    applyParameters(parameterIndexMap, preparedStatement, parameters);
  }

  private void applyParameters(Map<String, Integer> parameterIndexMap,
      PreparedStatement preparedStatement, Map<String, SqlParameterValue> lparameters)
      throws SQLException {
    for (Entry<String, SqlParameterValue> entry : lparameters.entrySet()) {
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

