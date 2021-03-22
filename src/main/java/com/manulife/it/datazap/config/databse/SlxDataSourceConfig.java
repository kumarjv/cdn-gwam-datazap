package  com.manulife.it.datazap.config.databse;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * SlxDataSourceConfig - Configuration class for SLX Database.
 * 
 */
@Configuration
//@ComponentScan("com.manulife.it.grs.agreement.repository.slx")
public class SlxDataSourceConfig {

  /**
   * DataSourceProperties for SLX database.
   * 
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.slx.datasource")
  @Profile("!test")
  public DataSourceProperties slxDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for SLX database.
   * 
   * @return DataSource {@link DataSource}.
   */
  @Bean
  @Profile("!test")
  public DataSource slxDataSource() {
    DataSourceProperties slxDataSourceProperties = slxDataSourceProperties();
    return DataSourceBuilder.create()
      .driverClassName(slxDataSourceProperties.getDriverClassName())
      .url(slxDataSourceProperties.getUrl())
      .username(slxDataSourceProperties.getUsername())
      .password(slxDataSourceProperties.getPassword())
      .build();
  }

  /**
   * NamedParameterJdbcTemplate for SLX database.
   * 
   * @param slxDataSource
   *          - SLX Datasource.
   * @return NamedParameterJdbcTemplate {@link NamedParameterJdbcTemplate}.
   */
  @Bean(
    name = "slxNamedJdbcTemplate")
  @Profile("!test")
  public NamedParameterJdbcTemplate
    slxNamedParameterJdbcTemplate(@Qualifier("slxDataSource") DataSource slxDataSource) {
    return new NamedParameterJdbcTemplate(slxDataSource);
  }

  /**
   * NamedParameterJdbcTemplate for SLX H2 Test database.
   * 
   * @param slxDataSource
   *          - H2 DB Datasource.
   * @return NamedParameterJdbcTemplate {@link NamedParameterJdbcTemplate}.
   */
  @Bean(
    name = "slxNamedJdbcTemplate")
  @Profile("test")
  public NamedParameterJdbcTemplate slxTestNamedParameterJdbcTemplate(DataSource slxDataSource) {
    return new NamedParameterJdbcTemplate(slxDataSource);
  }
}
