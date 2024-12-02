package org.acme.common.migration;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLDialect {

  /**
   * SQL para crear la tabla de logs de migraciones.
   * 
   * @return La sentencia SQL para crear la tabla.
   */
  String createLogTable(String name);

  /**
   * SQL para crear la tabla de locks para las migraciones.
   * 
   * @return La sentencia SQL para crear la tabla.
   */
  String createLockTable(String name);

  /**
   * SQL para insertar un valor en la tabla de locks.
   * 
   * @return La sentencia SQL para insertar un valor.
   */
  default String insertLock(String name) {
    return "INSERT INTO " + name + "_lock (id, locked, granted) VALUES (1, 0, NULL)";
  }

  /**
   * SQL para liberar el lock de migración.
   *
   * @return La sentencia SQL para liberar el lock.
   */
  default String releaseLock(String name) {
    return "UPDATE " + name + "_lock SET locked = 0, granted = NULL WHERE id = 1";
  }

  /**
   * SQL para marcar una migración como exitosa.
   * 
   * @param exists Indica si el registro ya existe.
   * @return La sentencia SQL para marcar la migración como exitosa.
   */
  default String markOkSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name
            + " SET md5sum=?, error=NULL, execution=NOW() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, name, filename, error, execution) VALUES (?, NULL, ?, ?, NOW())";
  }

  /**
   * SQL para marcar una migración como fallida.
   *
   * @param exists Indica si el registro ya existe.
   * @return La sentencia SQL para marcar la migración como fallida.
   */
  default String markFailSql(String name, boolean exists) {
    return exists
        ? "UPDATE " + name + " SET md5sum=?, error=?, execution=NOW() WHERE name=? AND filename=?"
        : "INSERT INTO " + name
            + " (md5sum, error, name, filename, execution) VALUES (?, ?, ?, ?, NOW())";
  }

  /**
   * SQL para obtener las migraciones ejecutadas.
   *
   * @return La sentencia SQL para obtener las migraciones ejecutadas.
   */
  default String listExecutedSql(String name) {
    return "SELECT filename, md5sum, error FROM " + name + " WHERE name = ? ORDER BY execution ASC";
  }

  /**
   * SQL para actualizar el estado de lock a activo.
   *
   * @return La sentencia SQL para actualizar el estado del lock.
   */
  default String updateLock(String name) {
    return "UPDATE " + name + " SET locked = 1, granted = NOW() WHERE id = 1";
  }

  /**
   * SQL para seleccionar el estado del lock.
   *
   * @return La sentencia SQL para consultar el estado del lock.
   */
  default String selectLock(String name) {
    return "SELECT locked FROM " + name + " WHERE id = 1";
  }

  /**
   * Interpreta el valor de la columna 'locked' desde el ResultSet.
   *
   * @param rs El ResultSet que contiene la columna.
   * @return true si 'locked' es activo, false en caso contrario.
   * @throws SQLException Si ocurre un error al leer el ResultSet.
   */
  default boolean interpretLocked(ResultSet rs) throws SQLException {
    return rs.getInt("locked") == 1;
  }

}

