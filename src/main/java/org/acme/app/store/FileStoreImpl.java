package org.acme.app.store;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.acme.common.store.BinaryContent;
import org.acme.common.store.FileStore;
import org.acme.common.store.RepositoryLink;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Repositorio de ficheros usando apache virtual file system
 *
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class FileStoreImpl implements FileStore {
  /**
   * El prefijo de url para los ficheros almacenados internamente.
   */
  private static final String UPLOAD_SCHEMA = "upload://";

  private final DataSource datasource;
  private final int tempCapacity = 1000;
  private final int minutesLifeTime = 60;

  private void cleanTemp() throws SQLException {
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement prepareStatement =
          connection.prepareStatement("DELETE FROM _filestorer where temp = 1 and upload < ?")) {
        prepareStatement.setTimestamp(1,
            new Timestamp(System.currentTimeMillis() - minutesLifeTime * 60000));
        prepareStatement.executeUpdate();
      }
      List<String> uids = new ArrayList<>();
      try (PreparedStatement prepareStatement = connection.prepareStatement(
          "SELECT code FROM _filestorer where temp = 1 order by upload desc limit 1000000 offset ?")) {
        prepareStatement.setInt(1, tempCapacity);
        try (ResultSet executeQuery = prepareStatement.executeQuery()) {
          while (executeQuery.next()) {
            uids.add(executeQuery.getString(1));
          }
        }
      }
      if (!uids.isEmpty()) {
        String secureQuery =
            String.format("DELETE FROM _filestorer where temp = 1 and code in (%s)",
                uids.stream().collect(Collectors.joining(",")));
        try (PreparedStatement prepareStatement = connection.prepareStatement(secureQuery)) {
          prepareStatement.executeUpdate();
        }
      }
    }
  }

  @Override
  public CompletionStage<RepositoryLink> commitContent(final String path) {
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement updateStatement = connection
          .prepareStatement("UPDATE _filestorer SET upload = ?, temp = 0 where code = ?")) {
        updateStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        updateStatement.setString(2, code(path));
        if (updateStatement.executeUpdate() != 1) {
          throw new IllegalArgumentException("Imposible insertar para " + path);
        }
        return CompletableFuture.completedFuture(RepositoryLink.builder().key(path).build());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public CompletionStage<RepositoryLink> replaceContent(String key, BinaryContent content) {
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement updateStatement = connection.prepareStatement(
          "UPDATE _filestorer SET name = ?, mime = ?, upload = ?, bytes = ? WHERE code = ?")) {
        updateStatement.setString(1, content.getName());
        updateStatement.setString(2, content.getContentType());
        updateStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        updateStatement.setBinaryStream(4, content.getInputStream());
        updateStatement.setString(5, code(key));
        if (updateStatement.executeUpdate() != 1) {
          throw new IllegalArgumentException("Imposible updatear para " + key);
        }
        return CompletableFuture.completedFuture(RepositoryLink.builder().key(key).build());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public CompletionStage<Boolean> deleteFile(final String path) {
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement prepareStatement =
          connection.prepareStatement("DELETE from _filestorer where code = ?")) {
        prepareStatement.setString(1, code(path));
        if (prepareStatement.executeUpdate() != 1) {
          log.warn("Imposible borrar para " + path);
        }
        return CompletableFuture.completedFuture(true);
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }

  }

  private CompletionStage<Optional<BinaryContent>> retrieve(final String path,
      final boolean temporal) {
    try {
      cleanTemp();
    } catch (SQLException ex) {
      log.error("Unable to clear temp");
    }
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement prepareStatement = connection.prepareStatement(
          "Select name, mime, bytes, upload from _filestorer where code = ? and temp=?")) {
        prepareStatement.setString(1, code(path));
        prepareStatement.setInt(2, temporal ? 1 : 0);
        try (ResultSet executeQuery = prepareStatement.executeQuery()) {
          Optional<BinaryContent> dataSource;
          if (executeQuery.next()) {
            String name = executeQuery.getString(1);
            InputStream fcontent = executeQuery.getBinaryStream(3);
            BinaryContent content = BinaryContent.builder().name(name)
                .contentType(executeQuery.getString(2)).inputStream(fcontent)
                .lastModification(executeQuery.getTimestamp(4).getTime()).build();
            dataSource = Optional.of(content);
          } else {
            dataSource = Optional.empty();
          }
          System.out.println("READ " + dataSource);
          return CompletableFuture.completedFuture(dataSource);
        }
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String code(String path) {
    return path.startsWith(UPLOAD_SCHEMA) ? path.replace(UPLOAD_SCHEMA, "") : path;
  }

  @Override
  public CompletionStage<Optional<BinaryContent>> retrieveTemp(String key) {
    return retrieve(key, true);
  }

  @Override
  public CompletionStage<Optional<BinaryContent>> retrieveFile(String key) {
    return retrieve(key, false);
  }

  @Override
  public CompletionStage<RepositoryLink> tempStore(BinaryContent source) {
    return store(source, true);
  }

  private CompletionStage<RepositoryLink> store(BinaryContent source, boolean temporal) {
    try {
      cleanTemp();
    } catch (SQLException ex) {
      log.error("Unable to clear temp");
    }
    try (Connection connection = datasource.getConnection()) {
      try (PreparedStatement updateStatement = connection.prepareStatement(
          "INSERT INTO _filestorer (code, temp, name, mime, upload, bytes) VALUES (?, ?, ?, ?, ?, ?)")) {
        final String path = UUID.randomUUID().toString();
        updateStatement.setString(1, path);
        updateStatement.setInt(2, 1);
        updateStatement.setString(3, source.getName());
        updateStatement.setString(4, source.getContentType());
        updateStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        updateStatement.setBinaryStream(6, source.getInputStream());
        if (updateStatement.executeUpdate() != 1) {
          throw new IOException("Imposible insertar para " + path);
        }
        return CompletableFuture.completedFuture(RepositoryLink.builder().key(path).build());
      }
    } catch (IOException | SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
