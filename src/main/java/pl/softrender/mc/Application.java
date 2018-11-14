package pl.softrender.mc;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class Application {
    @Value("${mc.api.path}")
    String contextPath;

//    @Bean
//    CamelContext camelContext(ApplicationContext applicationContext) throws Exception{
//        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
//        camelContext.start();
//        return camelContext;
//    }

//    @Bean
//    public RoutesBuilder routeBuilder() {
//        return new RestApi();
//    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean<CamelHttpTransportServlet> servlet = new ServletRegistrationBean<>(
                new CamelHttpTransportServlet(), contextPath + "/*"
        );
        servlet.setName("Camel servlet");
        return servlet;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
