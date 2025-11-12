package com.amazonitrics.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "dadosExternosEntityManagerFactory",
    transactionManagerRef = "dadosExternosTransactionManager",
    basePackages = {"com.amazonitrics.repository.external"}
)
@EntityScan(basePackages = {"com.amazonitrics.model.external"})
public class DadosExternosDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.jpa.external")
    public JpaProperties dadosExternosJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.external")
    public DataSourceProperties dadosExternosDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.external.hikari")
    public DataSource dadosExternosDataSource(@Qualifier("dadosExternosDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "dadosExternosEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dadosExternosEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dadosExternosDataSource") DataSource dataSource,
            @Qualifier("dadosExternosJpaProperties") JpaProperties jpaProperties) {

        Map<String, Object> hibernateProps = new HashMap<>();
        hibernateProps.putAll(jpaProperties.getProperties());

        String ddlAuto = jpaProperties.getProperties().get("hibernate.hbm2ddl.auto");
        if (ddlAuto == null) {
            ddlAuto = jpaProperties.getProperties().get("hibernate.ddl-auto");
        }
        if (ddlAuto != null) {
            hibernateProps.put("hibernate.hbm2ddl.auto", ddlAuto);
        } else {
            hibernateProps.put("hibernate.hbm2ddl.auto", "update");
        }

        hibernateProps.put("hibernate.show_sql", String.valueOf(jpaProperties.isShowSql()));

        if (jpaProperties.getDatabasePlatform() != null) {
            hibernateProps.put("hibernate.dialect", jpaProperties.getDatabasePlatform());
        }

        return builder
                .dataSource(dataSource)
                .properties(hibernateProps)
                .packages("com.amazonitrics.model.external")
                .persistenceUnit("dadosExternosPU")
                .build();
    }

    @Bean(name = "dadosExternosTransactionManager")
    public PlatformTransactionManager dadosExternosTransactionManager(
            @Qualifier("dadosExternosEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}