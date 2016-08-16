package ru.javarush.spring.config.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.TimeZone;

/**
 * Created by eGa on 15.08.2016.
 */
@Component
public class DbConstants {

    @Autowired
    private TimeZoneBean timeZoneBean;

    /**
     * Конструкторы
     */
    public DbConstants() {
        // Конструктор по умолчанию
    }

    /**
     * Геттеры
     */
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    public String getConnectionUrl() {
        return "jdbc:mysql://localhost:3306/?serverTimezone=" + timeZoneBean.getIdByTimeZone(TimeZone.getDefault()) + "&useSSL=false";
    }

    public String getUsername() {
        return "root";
    }

    public String getPassword() {
        return "root";
    }

    public String getDialect() {
        return "org.hibernate.dialect.MySQLDialect";
    }

    public String getShowSql() {
        return "false"; // логировать или нет sql запросы
    }

    public String getFormatSql() {
        return "false"; // форматировать или нет логированные sql запросы
    }

    public String[] getPackagesWithEntitiesToScan() {
        return new String[] {"ru.javarush.tasks.crud.model.entities"};
    }

    public String getPersistenceUnitName() {
        return "ru.javarush.entity.manager.factory"; // - это не имя пакета, это просто имя
    }

    public String getCurrentSessionContextClass() {
        return "org.springframework.orm.hibernate5.SpringSessionContext";  // spring-вая реализация интерфейса org.hibernate.context.spi.CurrentSessionContext лучше работает со spring-овыми транзакциями, чем все реализации этого интерфейса самим hibernate
               //"org.hibernate.context.internal.ThreadLocalSessionContext"; // или просто "thread"
               //"org.hibernate.context.internal.JTASessionContext";         // или просто "jta"
               //"org.hibernate.context.internal.ManagedSessionContext";     // или просто "managed"
    }

    /**
     * Методы
     */
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDialect());
        properties.put("hibernate.show_sql", getShowSql());
        properties.put("hibernate.format_sql", getFormatSql());
        properties.put("hibernate.connection.url", getConnectionUrl());
        properties.put("hibernate.connection.username", getUsername());
        properties.put("hibernate.connection.password", getPassword());
        properties.put("hibernate.current_session_context_class", getCurrentSessionContextClass()); // Лечит ошибку: org.hibernate.HibernateException: No CurrentSessionContext configured!
        return properties;
    }

}