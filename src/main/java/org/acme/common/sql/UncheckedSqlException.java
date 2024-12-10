package org.acme.common.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class UncheckedSqlException extends RuntimeException {
  private static final long serialVersionUID = 4262062984330839677L;

  public static UncheckedSqlException exception(Connection connection, SQLException sqlException) {
    String databaseProductName = getDatabaseProductName(connection);
    int errorCode = sqlException.getErrorCode();
    if (isDuplicateKeyError(databaseProductName, errorCode)) {
      return new NotUniqueException("Duplicate key error: " + sqlException.getMessage(),
          sqlException);
    } else if (isReferentialIntegrityError(databaseProductName, errorCode)) {
      return new NotExistentReferenceException(
          "Referential integrity violation: " + sqlException.getMessage(), sqlException);
    } else if (isCascadeDeleteError(databaseProductName, errorCode)) {
      return new NotEmptyChildsException("Cascade delete violation: " + sqlException.getMessage(),
          sqlException);
    } else {
      return new UncheckedSqlException("SQL error: " + sqlException.getMessage(), sqlException);
    }
  }
  
  private static String getDatabaseProductName(Connection connection) {
    try {
      DatabaseMetaData metaData = connection.getMetaData();
      return metaData.getDatabaseProductName();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Unable to retrieve database product name", e);
    }
  }

  private static boolean isDuplicateKeyError(String databaseProductName, int errorCode) {
    switch (databaseProductName) {
      case "PostgreSQL":
        return errorCode == 23505; // Unique violation
      case "Oracle":
        return errorCode == 1; // Unique constraint violated
      case "Microsoft SQL Server":
        return errorCode == 2627 || errorCode == 2601; // Unique constraint or duplicate key
      case "MySQL":
      case "MariaDB":
        return errorCode == 1062; // Duplicate entry
      case "H2":
        return errorCode == 23505; // Unique constraint violation
      default:
        return false;
    }
  }

  private static boolean isReferentialIntegrityError(String databaseProductName, int errorCode) {
    switch (databaseProductName) {
      case "PostgreSQL":
        return errorCode == 23503; // Foreign key violation
      case "Oracle":
        return errorCode == 2291; // Parent key not found
      case "Microsoft SQL Server":
        return errorCode == 547; // Foreign key constraint violation
      case "MySQL":
      case "MariaDB":
        return errorCode == 1452; // Cannot add or update child row
      case "H2":
        return errorCode == 23506; // Referential integrity violation
      default:
        return false;
    }
  }

  private static boolean isCascadeDeleteError(String databaseProductName, int errorCode) {
    switch (databaseProductName) {
      case "PostgreSQL":
        return errorCode == 23504; // Restrict violation
      case "Oracle":
        return errorCode == 2292; // Child record found
      case "Microsoft SQL Server":
        return errorCode == 547; // Foreign key constraint violation (delete cascade)
      case "MySQL":
      case "MariaDB":
        return errorCode == 1451; // Cannot delete or update parent row
      case "H2":
        return errorCode == 23504; // Restrict violation
      default:
        return false;
    }
  }

  
  public UncheckedSqlException(SQLException ex) {
    super(ex);
  }

  public UncheckedSqlException(String msg, SQLException ex) {
    super(msg, ex);
  }

}
