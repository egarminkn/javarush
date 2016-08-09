package ru.javarush.installer;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

/**
 * Created by eGarmin
 */
public class Creator {

    public DataSource getDatasource(String login, String pass, String url, String port){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // com.mysql.jdbc.Driver - deprecated
        dataSource.setUsername(login);
        dataSource.setPassword(pass);
        dataSource.setUrl(url + ":" + port + "/?serverTimezone=UTC&useSSL=false");
        return dataSource;
    }

}
