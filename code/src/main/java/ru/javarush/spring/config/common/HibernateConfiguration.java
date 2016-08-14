package ru.javarush.spring.config.common;

import java.util.Properties;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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
    private TimeZoneBean timeZoneBean;

    @Bean // Создаст бин dataSource
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/?serverTimezone=" + timeZoneBean.getIdByTimeZone(TimeZone.getDefault()) + "&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "false"); // логировать или нет sql запросы
        properties.put("hibernate.format_sql", "false");

        // Лечит ошибку: org.hibernate.HibernateException: No CurrentSessionContext configured!
        properties.put("hibernate.current_session_context_class",
                            "org.hibernate.context.internal.ThreadLocalSessionContext"); // или просто "thread"
                            //"org.hibernate.context.internal.JTASessionContext");         // или просто "jta"
                            //"org.hibernate.context.internal.ManagedSessionContext");     // или просто "managed"

        return properties;
    }

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

    /**
     * Далее идут бины необходимые для того, чтобы вместо hibernate Criteria использовать jpa Criteria
     * через все тот же hibernate. Однако в этом случае hibernate выступает в качетстве JpaVendorAdapter
     */

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean
    @Autowired
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds, JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(ds);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("ru.javarush.entity.manager.factory");
        localContainerEntityManagerFactoryBean.setPackagesToScan("ru.javarush.tasks.crud.model.entities");
//        Properties props = new Properties();
//        props.put("hibernate.dialect", );
//        props.put("hibernate.hbm2ddl.auto", );
//        props.put("hibernate.show_sql", );
//        props.put("hibernate.format_sql", );
        localContainerEntityManagerFactoryBean.setJpaProperties(hibernateProperties());
        //localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        //localContainerEntityManagerFactoryBean.setPersistenceProvider();
        //localContainerEntityManagerFactoryBean.setPersistenceXmlLocation("");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    @Autowired
//    @Primary
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
        HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
        factory.setEntityManagerFactory(emf);
        return factory;
    }

}