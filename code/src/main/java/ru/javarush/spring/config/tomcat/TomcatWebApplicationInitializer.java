package ru.javarush.spring.config.tomcat;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by eGarmin
 */
@Configuration
@EnableWebMvc
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) // При деплое в чистый Tomcat не будет пытаться инициализировать dataSource bean
public class TomcatWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * Дополнение к web.xml. В начале читается web.xml - потом этот метод может внести правки и дополнения
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(DispatcherConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // Задание init-param параметров сервлета
        XmlWebApplicationContext flowContext = new XmlWebApplicationContext();
        flowContext.setConfigLocation("/WEB-INF/spring/webflow/spring-config.xml");
        // Определение сервлета
        ServletRegistration.Dynamic flowDispatcher = servletContext.addServlet("flow-dispatcher", new DispatcherServlet(flowContext));
        flowDispatcher.setLoadOnStartup(1);
        flowDispatcher.addMapping("/webflow/*");
    }

}
