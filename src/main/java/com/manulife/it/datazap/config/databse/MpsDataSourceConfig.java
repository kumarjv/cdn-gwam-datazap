package com.manulife.it.datazap.config.databse;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * MpsDataSourceConfig - Configuration class for MPS Database.
 * 
 */
@Configuration
//@ComponentScan("com.manulife.it.grs.agreement.repository.mps")
public class MpsDataSourceConfig {

  /**
   * DataSourceProperties for MPS database.
   * 
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.mps.datasource")
  @Profile("!test")
  public DataSourceProperties mpsDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for MPS database.
   * 
   * @return DataSource {@link DataSource}.
   */
  @Bean
  @Profile("!test")
  public DataSource mpsDataSource() {
    DataSourceProperties mpsDataSourceProperties = mpsDataSourceProperties();
    return DataSourceBuilder.create()
      .driverClassName(mpsDataSourceProperties.getDriverClassName())
      .url(mpsDataSourceProperties.getUrl())
      .username(mpsDataSourceProperties.getUsername())
      .password(mpsDataSourceProperties.getPassword())
      .build();
  }

  /**
   * NamedParameterJdbcTemplate for MPS database.
   * 
   * @param mpsDataSource
   *          - MPS Datasource.
   * @return NamedParameterJdbcTemplate {@link NamedParameterJdbcTemplate}.
   */
  @Bean(
    name = "mpsNamedJdbcTemplate")
  @Profile("!test")
  public NamedParameterJdbcTemplate
    mpsNamedParameterJdbcTemplate(@Qualifier("mpsDataSource") DataSource mpsDataSource) {
    return new NamedParameterJdbcTemplate(mpsDataSource);
  }
  
  /**
   * DataSourceProperties for MPS TST database in test cases.
   * 
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.datasource")
  @Profile("test")
  @Primary
  public DataSourceProperties mpsDataSourcePropertiesTst() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for MPS TST database in test cases.
   * 
   * @return DataSource {@link DataSource}.
   */
  @Bean(
    name = "mpsDataSource")
  @Profile("test")
  public DataSource mpsDataSourceTst() {
    DataSourceProperties mpsDataSourceProperties = mpsDataSourcePropertiesTst();
    return DataSourceBuilder.create()
      .url(mpsDataSourceProperties.getUrl())
      .build();
  }

  /**
   * NamedParameterJdbcTemplate for MPS TST database in test cases.
   * 
   * @param mpsDataSource
   *          - H2 DB Datasource.
   * @return NamedParameterJdbcTemplate {@link NamedParameterJdbcTemplate}.
   */
  @Bean(
    name = "mpsNamedJdbcTemplate")
  @Profile("test")
  public NamedParameterJdbcTemplate
    mpsTestNamedParameterJdbcTemplate(@Qualifier("mpsDataSource") DataSource mpsDataSource) {
    return new NamedParameterJdbcTemplate(mpsDataSource);
  }
}
