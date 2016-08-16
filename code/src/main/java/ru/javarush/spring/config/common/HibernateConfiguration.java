package ru.javarush.spring.config.common;

import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;

//import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.SessionFactory;

//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Created by eGarmin
 */
@Configuration
@EnableTransactionManagement // - это аналог этого: <tx:annotation-driven transaction-manager="transactionManager"/>
public class HibernateConfiguration {

    @Autowired
    private DbConstants dbConstants;

    @Bean // Создаст бин dataSource
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbConstants.getDriverClassName());
        dataSource.setUrl(dbConstants.getConnectionUrl());
        dataSource.setUsername(dbConstants.getUsername());
        dataSource.setPassword(dbConstants.getPassword());
        return dataSource;
    }

    @Bean // Создаст бин jpaVendorAdapter
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(dbConstants.getShowSql()));
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform(dbConstants.getDialect());
        return hibernateJpaVendorAdapter;
    }

    @Bean // Создаст бин entityManagerFactory
    @Autowired
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds, JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(ds);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName(dbConstants.getPersistenceUnitName());
        localContainerEntityManagerFactoryBean.setPackagesToScan(dbConstants.getPackagesWithEntitiesToScan());
        localContainerEntityManagerFactoryBean.setJpaProperties(dbConstants.getHibernateProperties());
        //localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Это прокси sessionFactory для доступа к JPA-ному EntityManagerFactory
     */
    @Bean // Создаст бин sessionFactory
    @Autowired
//    @Primary
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
        HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
        factory.setEntityManagerFactory(emf);
        return factory;
    }

    /**
     * Это чисто Hibernate-ский sessionFactory без JPA-ного EntityManagerFactory
     */
//    @Bean // Создаст бин sessionFactory
//    @Autowired // По имени класса DataSource подтянет бин dataSource()
//    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(ds);
//        sessionFactory.setPackagesToScan(new String[] { "ru.javarush.tasks.crud.model.entities" }); // где искать Entity
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//    }

    @Bean // Создаст бин transactionManager
    @Autowired // По имени класса SessionFactory подтянет бин sessionFactory()
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sf);
        return txManager;
    }

}