package com.PracticeSpringBoot.ConnectMultipleDatabase.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",
        basePackages = {"com.PracticeSpringBoot.ConnectMultipleDatabase.bookRepository"},
        transactionManagerRef = "bookTransactionManager"
)
public class BookDbConfig {

    /**
     * Creates a data source for the book application.
     *
     * @return         	The created data source for the book application.
     */
    @Bean(name = "bookDatasource")
    @ConfigurationProperties(prefix = "spring.book.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configures and creates the entity manager factory bean for the book database.
     *
     * @param entityManagerFactoryBuilder The entity manager factory builder.
     * @param dataSource The data source for the book database.
     * @return The configured entity manager factory bean.
     */
    @Bean(name = "bookEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("bookDatasource") DataSource dataSource) {
        // Configure the properties for the entity manager factory
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");

        // Build the entity manager factory bean with the configured properties
        return entityManagerFactoryBuilder.dataSource(dataSource).properties(properties)
                .packages("com.PracticeSpringBoot.ConnectMultipleDatabase.modelBook")
                .persistenceUnit("Book").build();
    }

    /**
     * Returns the transaction manager for the book entity manager factory.
     *
     * @param bookentityManagerFactory the book entity manager factory
     * @return the transaction manager
     */
    @Bean(name = "bookTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("bookEntityManagerFactory") EntityManagerFactory bookentityManagerFactory) {
        return new JpaTransactionManager(bookentityManagerFactory);
    }
}
