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
public class UserRootCreator extends Creator {

    @RequestMapping(value = "/create-user-root", method = RequestMethod.POST)
    public String createUserRoot(@RequestParam(value="login", required=false, defaultValue="root")      String login,
                                 @RequestParam(value="pass",  required=false, defaultValue="root")      String pass,
                                 @RequestParam(value="url",   required=false, defaultValue="localhost") String url,
                                 @RequestParam(value="port",  required=false, defaultValue="3306")      String port,
                                 Model model) throws SQLException, IOException {
        DataSource dataSource = getDatasource(login, pass, url, port);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {
            BufferedReader rootUserSql = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sql/create-root-user.sql")));
            while (rootUserSql.ready()) {
                statement.addBatch(rootUserSql.readLine());
            }
            statement.executeBatch();
        }

        model.addAttribute("login", login);
        return "ru/javarush/installer/create-ok";
    }

    @RequestMapping(value = "/db-installer-step2", method = RequestMethod.GET)
    public String createUserRootForm() {
        return "ru/javarush/installer/create-user-root-form";
    }

}
