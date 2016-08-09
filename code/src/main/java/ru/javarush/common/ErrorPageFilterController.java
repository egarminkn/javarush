package ru.javarush.common;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by eGarmin
 * Реально действует только для spring boot. Для tomcat смотри файл web.xml тег error-page
 */
@Controller
public class ErrorPageFilterController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "ru/javarush/404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
