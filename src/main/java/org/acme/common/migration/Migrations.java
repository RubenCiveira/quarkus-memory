package org.acme.common.migration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.acme.features.fruit.infrastructure.driver.FruitResource;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import lombok.Data;

public class Migrations implements AutoCloseable {
  @Data
  private abstract static class Resource {
    private String name;

    public abstract InputStream open() throws IOException;
  }

  private static final Logger LOG = Logger.getLogger(FruitResource.class);

  private static final String LOG_TABLE = "_migrations";
  private static final String LOCK_TABLE = "_migrations_lock";
  private final String driver;
  private final SQLDialect dialect;
  private final Connection connection;
  private final String group;
  private final String migrationsIndex;

  public Migrations(Connection connection, String migrationsIndex, String group)
      throws SQLException {
    this.connection = connection;
    this.driver = connection.getMetaData().getDriverName().toLowerCase();
    this.dialect = getDialect(driver);
    this.group = group;
    this.migrationsIndex = migrationsIndex;
    createLogTable();
    createLockTable();
  }

  private static SQLDialect getDialect(String driver) {
    if (driver.contains("postgresql")) {
      return new PostgreSQLDialect();
    } else if (driver.contains("oracle")) {
      return new OracleDialect();
    } else if (driver.contains("mariadb")) {
      return new MySQLDialect();
    } else if (driver.contains("mysql")) {
      return new MySQLDialect();
    } else if (driver.contains("sqlserver")) {
      return new SQLServerDialect();
    } else if (driver.contains("h2")) {
      return new H2Dialect();
    } else {
      throw new UnsupportedOperationException("Unsupported database driver: " + driver);
    }
  }

  public Map<String, Object> runMigrations() throws SQLException {
    Map<String, Object> result = new HashMap<>();
    result.put("errors", new ArrayList<String>());
    result.put("queries", new ArrayList<String>());
    result.put("files", new ArrayList<String>());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Runnimg migrations");
    }
    if (lock()) {
      try {
        System.out.println("Vamos a intentar vre lo ");
        Map<String, Map<String, String>> previousMigrations = listExecuted(this.group);
        System.out.println("Tengo previos ");
        System.out.println(previousMigrations);
        List<String> files = retrieveResources(this.migrationsIndex);
        System.out.println("Tengo files " + files);
        List<String> toExecute = new ArrayList<>();
        boolean pending = false;

        for (String file : files) {
          System.out.println("MIRO A " + file);
          String md5 = calculateMd5(file);
          if (previousMigrations.containsKey(file)
              && previousMigrations.get(file).get("error") == null) {
            if (pending) {
              addError(result,
                  "The migration " + file + " was executed, but another migration is pending.");
            }
            String prevMd5 = previousMigrations.get(file).get("md5sum");
            if (!md5.equals(prevMd5)) {
              addError(result, "The file " + file + " has a different md5.");
            }
          } else {
            if (!pending) {
              pending = true;
            }
            toExecute.add(file);
          }
        }

        for (String file : toExecute) {
          String md5 = calculateMd5(file);
          if (file.endsWith(".sql")) {
            addFiles(result, "Processing file " + file);
            result = runSql(this.group, file, previousMigrations.containsKey(file), md5, result);
          }
        }
      } catch (Exception e) {
        System.err.println("====");
        System.err.println("====");
        System.err.println(e.getMessage());
        System.err.println("====");
        System.err.println("====");
        e.printStackTrace();
        addError(result, e.getMessage());
      } finally {
        unlock();
      }
    } else {
      addError(result, "The migrations are locked.");

    }
    return result;
  }

  private Map<String, Object> runSql(String name, String file, boolean exists, String md5,
      Map<String, Object> result) throws SQLException {
    try {
      List<String> lines = readFile(file);
      System.out.println("SQL: en " + file + " tengo a " + lines);
      List<String> filtered =
          lines.stream().filter(line -> !line.startsWith("--")).collect(Collectors.toList());
      System.out.println("Las filtradas son " + filtered);
      String sql = String.join("\n", filtered);
      String[] statements = sql.split(";");
      for (String statement : statements) {
        System.out.println("El statement es " + statement);
        if (!statement.trim().isEmpty()) {
          executeSql(statement);
          addQuery(result, statement);
        }
      }
      markOk(name, file, exists, md5);
    } catch (Exception e) {
      System.err.println(">> FALLO POR " + e.getMessage());
      markFail(name, file, exists, md5, e);
      addError(result, e.getMessage());
    }
    return result;
  }

  private void executeSql(String sql) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.execute();
    }
  }

  private void markOk(String name, String file, boolean exists, String md5) throws SQLException {
    String sql = dialect.markOkSql(LOG_TABLE, exists);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, md5);
      stmt.setString(2, name);
      stmt.setString(3, file);
      stmt.executeUpdate();
    }
  }

  private void markFail(String name, String file, boolean exists, String md5, Exception e)
      throws SQLException {
    String sql = dialect.markFailSql(LOG_TABLE, exists);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, md5);
      stmt.setString(2, e.getMessage());
      stmt.setString(3, name);
      stmt.setString(4, file);
      stmt.executeUpdate();
    }
  }

  private Map<String, Map<String, String>> listExecuted(String name) throws SQLException {
    createLogTable();
    String sql = dialect.listExecutedSql(LOG_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, name);
      try (ResultSet rs = stmt.executeQuery()) {
        Map<String, Map<String, String>> rows = new HashMap<>();
        while (rs.next()) {
          Map<String, String> row = new HashMap<>();
          row.put("md5sum", rs.getString("md5sum"));
          row.put("error", rs.getString("error"));
          rows.put(rs.getString("filename"), row);
        }
        return rows;
      }
    }
  }

  private void createLogTable() throws SQLException {
    String sql = dialect.createLogTable(LOG_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.execute();
    }
  }

  private void createLockTable() throws SQLException {
    String sql = dialect.createLockTable(LOCK_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.execute();
      try (PreparedStatement inert = connection.prepareStatement(dialect.insertLock(LOCK_TABLE))) {
        inert.execute();// Update(dialect.insertLock(LOCK_TABLE));
      }
    }
  }

  private boolean lock() throws SQLException {
    String sql = dialect.selectLock(LOCK_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next() && dialect.interpretLocked(rs)) {
          return false;
        }
      }
    }
    sql = dialect.updateLock(LOCK_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
      return true;
    }
  }

  private void unlock() throws SQLException {
    String sql = dialect.releaseLock(LOCK_TABLE);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    }
  }

  private String calculateMd5(String file) throws Exception {
    InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
    System.out.println("MD5: PARA [" + file + "] tengo a " + resourceAsStream);
    MessageDigest md = MessageDigest.getInstance("MD5");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        md.update(line.getBytes());
      }
    }
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  private List<String> readFile(String file) throws Exception {
    InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
    System.out.println("CODE: Para " + file + " tengo a  " + resourceAsStream);
    try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
      return br.lines().toList();
    }
  }

  @SuppressWarnings("unchecked")
  private void addError(Map<String, Object> result, String error) {
    if (LOG.isEnabled(Level.WARN)) {
      LOG.warn(error);
    }
    ((List<String>) result.get("errors")).add(error);
  }

  @SuppressWarnings("unchecked")
  private void addQuery(Map<String, Object> result, String query) {
    ((List<String>) result.get("queries")).add(query);
  }

  @SuppressWarnings("unchecked")
  private void addFiles(Map<String, Object> result, String query) {
    ((List<String>) result.get("files")).add(query);
  }

  @Override
  public void close() throws SQLException {
    connection.close();
  }

  private List<String> retrieveResources(String packageName) throws IOException {
    List<String> resourceMap = new ArrayList<>();
    InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(packageName);
    try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
      br.lines().forEach(line -> {
        resourceMap.add(line.startsWith("/") ? line.substring(1) : line);
      });
    }
    System.err.println("Los ficheros son " + resourceMap);
    return resourceMap;
  }
}
