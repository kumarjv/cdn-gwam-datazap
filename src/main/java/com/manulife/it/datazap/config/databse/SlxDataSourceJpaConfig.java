package com.manulife.it.datazap.config.databse;

import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*** Configuration class for SLX Database. **/

@Profile("!test")
@Configuration
/*@EntityScan(
  basePackages = "com.manulife.it.grs.agreement.entities.slx")
@EnableJpaRepositories(
  basePackages = "com.manulife.it.grs.agreement.account.ift.repository.slx.v1",
  entityManagerFactoryRef = "slxEntityManagerFactory",
  transactionManagerRef = "slxTransactionManager")*/
@EnableTransactionManagement
public class SlxDataSourceJpaConfig {

  /**
   * DataSourceProperties for SLX database.
   *
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.slx.datasource")
  @Profile("!test")
  public DataSourceProperties slxDataSourceProperties1() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for SLX database.
   *
   * @return DataSource {@link DataSource}.
   */
  @Bean
  @Profile("!test")
  public DataSource slxDataSource1() {
    DataSourceProperties slxDataSourceProperties = slxDataSourceProperties1();
    return DataSourceBuilder.create()
      .driverClassName(slxDataSourceProperties.getDriverClassName())
      .url(slxDataSourceProperties.getUrl())
      .username(slxDataSourceProperties.getUsername())
      .password(slxDataSourceProperties.getPassword())
      .build();
  }

  /**
   * PlatformTransactionManager for SLX database.
   *
   * @return PlatformTransactionManager {@link PlatformTransactionManager}.
   */
  @Bean
  public PlatformTransactionManager slxTransactionManager() {
    return new JpaTransactionManager(slxEntityManagerFactory().getObject());
  }

  /**
   * LocalContainerEntityManagerFactoryBean for SLX database.
   *
   * @return LocalContainerEntityManagerFactoryBean
   *         {@link LocalContainerEntityManagerFactoryBean}.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean slxEntityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(slxDataSource1());
    factory.setPackagesToScan("com.manulife.it.grs.agreement.entities.slx");

    final Properties jpaProperties = new Properties(NumberUtils.INTEGER_ONE);
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
    factory.setJpaProperties(jpaProperties);

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    factory.setJpaVendorAdapter(vendorAdapter);
    final HashMap<String, Object> properties = new HashMap<>(NumberUtils.INTEGER_ONE);
    properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
    factory.setJpaPropertyMap(properties);
    return factory;
  }
}
