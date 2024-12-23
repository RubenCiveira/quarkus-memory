package org.acme.common.sql;

import java.sql.Connection;
import java.sql.SQLException;

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
      throw UncheckedSqlException.exception(connection, ex);
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
      throw UncheckedSqlException.exception(connection, ex);
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
      throw UncheckedSqlException.exception(connection, ex);
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
      throw UncheckedSqlException.exception(connection, ex);
    }
  }

  /* default */ Connection currentConnection() {
    return connection;
  }

  public <T> SqlSchematicQuery<T> createSqlSchematicQuery(String table) {
    return new SqlSchematicQuery<>(this, table);
  }

  public <T> SqlQuery<T> createSqlQuery(String sql) {
    return new SqlQuery<>(this, sql);
  }

  public SqlCommand createSqlCommand(String sql) {
    return new SqlCommand(this, sql);
  }

}
