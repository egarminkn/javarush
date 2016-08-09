package ru.javarush.installer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by eGarmin
 */
@Controller
public class RowsUserTodoCreator extends Creator {

    @RequestMapping(value = "/mvc/create-rows", method = RequestMethod.POST)
    public String createRows(@RequestParam(value="login", required=false, defaultValue="root")                   String login,
                             @RequestParam(value="pass",  required=false, defaultValue="root")                   String pass,
                             @RequestParam(value="url",   required=false, defaultValue="jdbc:mysql://localhost") String url,
                             @RequestParam(value="port",  required=false, defaultValue="3306")                   String port,
                             Model model) throws SQLException, IOException {
        DataSource dataSource = getDatasource(login, pass, url, port);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {

            InputStream inputStream = new SequenceInputStream(getClass().getClassLoader().getResourceAsStream("sql/fill-user-table.sql"),
                                                              getClass().getClassLoader().getResourceAsStream("sql/fill-todo-table.sql"));
            BufferedReader rowsSql = new BufferedReader(new InputStreamReader(inputStream));
            String nextSql = null;
            while ((nextSql = rowsSql.readLine()) != null) {
                statement.addBatch(nextSql);
            }
            statement.executeBatch();
        }

        model.addAttribute("login", login);
        return "ru/javarush/installer/create-ok";
    }

    @RequestMapping(value = "/mvc/db-installer-step4", method = RequestMethod.GET)
    public String createRowsForm() {
        return "ru/javarush/installer/create-rows-user-todo-form";
    }

}
