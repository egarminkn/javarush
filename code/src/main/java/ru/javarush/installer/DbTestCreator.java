package ru.javarush.installer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by eGarmin
 */
@Controller
public class DbTestCreator extends Creator {

    @RequestMapping(value = "/mvc/create-db-test", method = RequestMethod.POST)
    public String createDbTest(@RequestParam(value="login", required=false, defaultValue="root")                   String login,
                               @RequestParam(value="pass",  required=false, defaultValue="root")                   String pass,
                               @RequestParam(value="url",   required=false, defaultValue="jdbc:mysql://localhost") String url,
                               @RequestParam(value="port",  required=false, defaultValue="3306")                   String port,
                               Model model) throws SQLException, IOException {
        DataSource dataSource = getDatasource(login, pass, url, port);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {

            BufferedReader testDbSql = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sql/create-db-test.sql")));
            while (testDbSql.ready()) {
                statement.addBatch(testDbSql.readLine());
            }
            statement.executeBatch();
        }

        return "redirect:/mvc/db-installer-step2"; // редирект возможен только на GET, поэтому параметры всегда открыты
    }

    @RequestMapping(value = "/mvc/db-installer-step1", method = RequestMethod.GET)
    public String createDbTestForm() {
        return "ru/javarush/installer/create-db-test-form";
    }

}