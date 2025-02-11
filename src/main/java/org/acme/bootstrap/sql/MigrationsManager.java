package org.acme.bootstrap.sql;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.acme.common.infrastructure.migration.Migrations;
import org.acme.common.infrastructure.sql.UncheckedSqlException;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.supplier.AgroalConnectionFactoryConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalConnectionPoolConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class MigrationsManager {
  private static final Map<String, String> DB_KIND_TO_DRIVER =
      Map.of("postgresql", "org.postgresql.Driver", "mysql", "com.mysql.cj.jdbc.Driver", "mariadb",
          "org.mariadb.jdbc.Driver", "h2", "org.h2.Driver", "oracle", "oracle.jdbc.OracleDriver",
          "sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

  public static interface JdbcConfig {
    Optional<String> url();

    Optional<Integer> maxSize();

    Optional<Integer> minSize();
  }

  @ConfigMapping(prefix = "app.migration")
  public static interface MigrationsConfig {
    Optional<JdbcConfig> jdbc();

    Optional<String> dbKind();

    Optional<String> username();

    Optional<String> password();

    Optional<String> location();

    Optional<String> group();
  }

  private final MigrationsConfig config;
  private final DataSource source;

  public void onInit(@Observes StartupEvent init) {
    DataSource migrationsSource = config.jdbc().flatMap(JdbcConfig::url).map(url -> {
      String driverClassName = DB_KIND_TO_DRIVER.get(config.dbKind().orElse(""));

      AgroalDataSourceConfigurationSupplier configurationSupplier =
          new AgroalDataSourceConfigurationSupplier();
      AgroalConnectionPoolConfigurationSupplier connectionPoolConfiguration =
          configurationSupplier.connectionPoolConfiguration();
      connectionPoolConfiguration.maxSize(config.jdbc().get().maxSize().orElse(1))
          .minSize(config.jdbc().get().minSize().orElse(1));
      // Configurar la conexión mínima
      AgroalConnectionFactoryConfigurationSupplier connectionFactoryConfiguration =
          connectionPoolConfiguration.connectionFactoryConfiguration();
      connectionFactoryConfiguration.jdbcUrl(url).credential(config.password().orElse(""))
          .principal(() -> config.username().orElse(""))
          .connectionProviderClassName(driverClassName);
      try {
        return (DataSource) AgroalDataSource.from(configurationSupplier.get());
      } catch (SQLException e) {
        throw new UncheckedSqlException(e);
      }
    }).orElseGet(() -> source);
    try (Migrations mig = new Migrations(migrationsSource.getConnection(),
        config.location().orElse("db/migration/changelog.txt"), config.group().orElse("-"))) {
      mig.runMigrations();
    } catch (SQLException ex) {
      throw new UncheckedSqlException(ex);
    }
  }
}
