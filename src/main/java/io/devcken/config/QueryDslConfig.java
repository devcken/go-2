package io.devcken.config;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("io.devcken")
public class QueryDslConfig {
  private final Environment environment;

  @Inject
  public QueryDslConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();

    dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
    dataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
    dataSource.setPassword(environment.getProperty("spring.datasource.password"));

    return dataSource;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

    jpaVendorAdapter.setDatabase(Database.H2);
    jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
    jpaVendorAdapter.setShowSql(true);
    jpaVendorAdapter.setGenerateDdl(true);

    return jpaVendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

    entityManagerFactoryBean.setPackagesToScan("io.devcken");
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
    entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

    return entityManagerFactoryBean;
  }

  @Bean
  public SQLQueryFactory queryFactory(DataSource dataSource) {
    com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(new H2Templates());

    return new SQLQueryFactory(configuration, dataSource);
  }
}
