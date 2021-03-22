package  com.manulife.it.datazap.config.databse;

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

/*** Configuration class for MPS Database. **/

@Profile("!test")
@Configuration
/*@EntityScan(
  basePackages = "com.manulife.it.grs.agreement.entities.mps")
@EnableJpaRepositories(
  basePackages = "com.manulife.it.grs.agreement.account.ift.repository.mps.v1",
  entityManagerFactoryRef = "mpsEntityManagerFactory",
  transactionManagerRef = "mpsTransactionManager")*/
@EnableTransactionManagement
public class MpsDataSourceJpaConfig {

  /**
   * DataSourceProperties for MPS database.
   *
   * @return DataSourceProperties {@link DataSourceProperties}.
   */
  @Bean
  @ConfigurationProperties(
    prefix = "spring.mps.datasource")
  @Profile("!test")
  public DataSourceProperties mpsDataSourceProperties1() {
    return new DataSourceProperties();
  }

  /**
   * DataSource for MPS database.
   *
   * @return DataSource {@link DataSource}.
   */
  @Bean
  @Profile("!test")
  public DataSource mpsDataSource1() {
    DataSourceProperties mpsDataSourceProperties = mpsDataSourceProperties1();
    return DataSourceBuilder.create()
      .driverClassName(mpsDataSourceProperties.getDriverClassName())
      .url(mpsDataSourceProperties.getUrl())
      .username(mpsDataSourceProperties.getUsername())
      .password(mpsDataSourceProperties.getPassword())
      .build();
  }

  /**
   * PlatformTransactionManager for MPS database.
   *
   * @return PlatformTransactionManager {@link PlatformTransactionManager}.
   */
  @Bean
  public PlatformTransactionManager mpsTransactionManager() {
    return new JpaTransactionManager(mpsEntityManagerFactory().getObject());
  }

  /**
   * LocalContainerEntityManagerFactoryBean for SLX database.
   *
   * @return LocalContainerEntityManagerFactoryBean
   *         {@link LocalContainerEntityManagerFactoryBean}.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean mpsEntityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(mpsDataSource1());
    factory.setPackagesToScan("com.manulife.it.grs.agreement.entities.mps");

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
