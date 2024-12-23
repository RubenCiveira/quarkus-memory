package org.acme.common.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AbstractSqlParametrizedUnitTest {

  @Mock
  private Connection connection;

  @Mock
  private PreparedStatement preparedStatement;

  @Mock
  private ResultSet resultSet;

  @Mock
  private DatabaseMetaData metaData;

  @Mock
  private SqlTemplate template;

  @Mock
  private SqlConverter<String> converter;

  private TestSqlParametrized testSql;

  private String dbType = "PostgreSQL";

  @BeforeEach
  void setUp() throws SQLException {
    MockitoAnnotations.openMocks(this);
    when(template.currentConnection()).thenReturn(connection);
    testSql = new TestSqlParametrized(template);
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getIdentifierQuoteString()).thenReturn("`");
    when(metaData.getDatabaseProductName()).then(call -> dbType);
  }

  @Test
  void testExecuteUpdate() throws SQLException, InterruptedException, ExecutionException {
    int result = testSql.executeUpdate("UPDATE table SET column = ? WHERE id = ?")
        .toCompletableFuture().get();
    assertEquals(1, result);
    verify(preparedStatement, times(1)).executeUpdate();
  }

  @Test
  void testExecuteUpdateThrowsSQLException() throws SQLException {
    when(preparedStatement.executeUpdate()).thenThrow(new SQLException("SQL Error"));
    assertThrows(UncheckedSqlException.class,
        () -> testSql.executeUpdate("UPDATE table SET column = ? WHERE id = ?"));
  }

  @Test
  void testExecuteQueryOne() throws SQLException, InterruptedException, ExecutionException {
    when(resultSet.next()).thenReturn(true, false);
    when(converter.convert(Mockito.any())).thenReturn(Optional.of("Result"));

    SqlResult<String> result = testSql.with("_age", SqlParameterValue.of(22))
        .executeQuery("SELECT * FROM table where age = :_age", converter).toCompletableFuture()
        .get();

    Optional<String> one = result.one();
    assertTrue(one.isPresent());
    assertEquals("Result", one.get());

    verify(preparedStatement, times(1)).executeQuery();
  }

  @SuppressWarnings("unchecked")
  @Test
  void testExecuteQueryAll() throws SQLException, InterruptedException, ExecutionException {
    when(resultSet.next()).thenReturn(true, true, false);
    when(converter.convert(Mockito.any())).thenReturn(Optional.of("Result1"),
        Optional.of("Result2"));

    SqlResult<String> result = testSql.with("name", SqlParameterValue.of("green"))
        .executeQuery("SELECT * FROM table where name = :name and :", converter)
        .toCompletableFuture().get();

    List<String> all = result.all();
    assertEquals(2, all.size());
    assertEquals("Result1", all.get(0));
    assertEquals("Result2", all.get(1));

    verify(preparedStatement, times(1)).executeQuery();

    result.limit(Optional.of(1));

    verify(preparedStatement, times(2)).executeQuery();
  }

  @Test
  void testFormatSqlWithLists() throws SQLException, InterruptedException, ExecutionException {
    testSql.with("params", SqlListParameterValue.of("one", "two", "three", "four"))
        .with("name", SqlParameterValue.of("his-name"))
        .executeQuery(
            "SELECT * FROM \"table\" where di IN(:params) and name = :name and marca = '\"uno :22 que : viene\"' :",
            converter)
        .toCompletableFuture().get().limit(10);

    verify(preparedStatement).setString(1, "one");
    verify(preparedStatement).setString(2, "two");
    verify(preparedStatement).setString(3, "three");
    verify(preparedStatement).setString(4, "four");
    verify(preparedStatement).setString(5, "his-name");
    verify(connection).prepareStatement(
        "SELECT * FROM `table` where di IN (?, ?, ?, ?) and name = ? and marca = '\"uno :22 que : viene\"' : limit 10");
  }

  @Test
  void testFormatErrors() throws SQLException, InterruptedException, ExecutionException {
    SqlResult<String> other = testSql.with("params", SqlParameterValue.of("four"))
        .executeQuery("SELECT * FROM \"table\" where name = :name and color = :name", converter)
        .toCompletableFuture().get();
    assertThrows(IllegalArgumentException.class, () -> other.all());
    SqlResult<String> one = testSql.with("name", SqlParameterValue.of("oo"))
        .with("params", SqlListParameterValue.of("one", "two", "three", "four"))
        .executeQuery("SELECT * FROM \"table\" where di IN(:listParams)", converter)
        .toCompletableFuture().get();
    assertThrows(IllegalArgumentException.class, () -> one.all());
    SqlResult<String> three = testSql.executeQuery(
        "SELECT * FROM \"table\" where name = :name and di IN(:params) and do IN(:others)",
        converter).toCompletableFuture().get();
    assertThrows(IllegalArgumentException.class, () -> three.all());
  }

  @Test
  void testConnectionErrors() throws SQLException, InterruptedException, ExecutionException {
    when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
    SqlResult<String> result = testSql.with("_age", SqlParameterValue.of(22))
        .executeQuery("SELECT * FROM table where age = :_age", converter).toCompletableFuture()
        .get();
    assertThrows(UncheckedSqlException.class, () -> result.one());
  }

  /*
   * @Test void testEscapeIdentifiers() throws SQLException { String sql =
   * "SELECT \"column\" FROM table WHERE name = \"identifier\""; String escapedSql =
   * testSql.escapeIdentifiers(sql); assertEquals(sql, escapedSql); }
   * 
   * @Test void testApplyParameters() throws SQLException { SqlParameterValue paramValue =
   * mock(SqlParameterValue.class); testSql.with("param", paramValue);
   * 
   * Map<String, Integer> paramMap = Map.of("param", 1); testSql.applyParameters(paramMap,
   * preparedStatement);
   * 
   * verify(paramValue, times(1)).accept(eq(1), eq(preparedStatement)); }
   * 
   * @Test void testReplaceInPlaceholders() { String sql = "SELECT * FROM table WHERE id IN (?)";
   * List<Integer> sizes = List.of(3); String result = testSql.replaceInPlaceholders(sql, sizes);
   * 
   * assertTrue(result.contains("IN (?, ?, ?)")); }
   */

  static class TestSqlParametrized extends AbstractSqlParametrized<TestSqlParametrized> {

    public TestSqlParametrized(SqlTemplate template) {
      super(template);
    }
  }
}
