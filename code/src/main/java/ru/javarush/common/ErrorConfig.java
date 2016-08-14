package ru.javarush.common;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by eGarmin
 * Реально действует только для spring boot. Для tomcat смотри файл web.xml тег error-page
 */
@Configuration
public class ErrorConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
                                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
    }

}