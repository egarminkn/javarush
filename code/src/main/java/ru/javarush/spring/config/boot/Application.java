package ru.javarush.spring.config.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.javarush.spring.config.tomcat.TomcatWebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableWebMvc
public class Application extends SpringBootServletInitializer implements ServletContextInitializer {

    private static boolean isSpringBoot = false;

    public static void main(String[] args) {
        isSpringBoot = true;
        SpringApplication.run(Application.class, args);
    }

    /**
     *  Замена web.xml для spring boot
     */
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        if (isSpringBoot) {
            new TomcatWebApplicationInitializer().onStartup(container);
        }
    }

}