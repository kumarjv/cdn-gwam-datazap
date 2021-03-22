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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * CanonicalDataSourceConfig - Configuration class for Canonical Database.
 * 
 */
@Profile("!test")
@Configuration
/*@EntityScan(
  basePackages = "com.manulife.it.grs.agreement.entities.canonical")
@EnableJpaRepositories(
  basePackages = "com.manulife.it.grs.agreement.repository.canonical",
  entityManagerFactoryRef = "canonicalEntityManagerFactory",
  transactionManagerRef = "canonicalTransactionManager")*/
@EnableTransactionManagement
public class CanonicalDataSourceConfig {
  
  /**
   * DataSourceProperties for Canonical database.
   * 
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.canonical.datasource")
  @Primary
  public DataSourceProperties canonicalDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for canonical database.
   * 
   * @return DataSource {@link DataSource}.
   */
  @Bean
  @Primary
  public DataSource canonicalDataSource() {
    DataSourceProperties canonicalDataSourceProperties = canonicalDataSourceProperties();
    return DataSourceBuilder.create()
      .driverClassName(canonicalDataSourceProperties.getDriverClassName())
      .url(canonicalDataSourceProperties.getUrl())
      .username(canonicalDataSourceProperties.getUsername())
      .password(canonicalDataSourceProperties.getPassword())
      .build();
  }
  
 
  /**
   * PlatformTransactionManager for canonical database.
   * 
   * @return PlatformTransactionManager {@link PlatformTransactionManager}.
   */
  @Bean
  @Primary
  public PlatformTransactionManager canonicalTransactionManager() {
    return new JpaTransactionManager(canonicalEntityManagerFactory().getObject());
  }

  /**
   * LocalContainerEntityManagerFactoryBean for canonical database.
   * 
   * @return LocalContainerEntityManagerFactoryBean {@link LocalContainerEntityManagerFactoryBean}.
   */
  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean canonicalEntityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(canonicalDataSource());
    factory.setPackagesToScan("com.manulife.it.grs.agreement.entities.canonical");

    final Properties jpaProperties = new Properties(NumberUtils.INTEGER_ONE);
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    factory.setJpaProperties(jpaProperties);

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    factory.setJpaVendorAdapter(vendorAdapter);
    final HashMap<String, Object> properties = new HashMap<>(NumberUtils.INTEGER_ONE);
    properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    factory.setJpaPropertyMap(properties);
    return factory;
  }
}
