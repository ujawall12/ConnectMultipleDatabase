package com.PracticeSpringBoot.ConnectMultipleDatabase.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "userTransactionManager", basePackages = {"com.PracticeSpringBoot.ConnectMultipleDatabase.userRepository"})
public class UserDbConfig {

    /**
     * Creates and configures a DataSource bean for the application.
     *
     * @return  the configured DataSource bean
     */
    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Creates and configures the entity manager factory bean.
     *
     * @param  entityManagerFactoryBuilder  the entity manager factory builder
     * @param  dataSource                   the data source
     * @return                              the configured entity manager factory bean
     */
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                           @Qualifier("datasource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        return entityManagerFactoryBuilder.dataSource(dataSource).properties(properties)
                .packages("com.PracticeSpringBoot.ConnectMultipleDatabase.modelUser")
                .persistenceUnit("User").build();
    }

    /**
     * Creates a new transaction manager for the user module.
     *
     * @param  entityManagerFactory  the entity manager factory used for managing entities
     * @return                       the transaction manager for the user module
     */
    @Bean(name = "userTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
