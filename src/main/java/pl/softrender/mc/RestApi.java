package pl.softrender.mc;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestApi extends RouteBuilder {

    @Value("${mc.api.path}")
    String contextPath;

    @Value("${server.port}")
    Integer serverPort;

    public void configure() {
        System.out.println("CONTEXT PATH " + contextPath);
        restConfiguration()
                .contextPath(contextPath)
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);
        rest("/api/")
                .id("api-route")
                .consumes("application/json")
                .post("/bean")
                .bindingMode(RestBindingMode.json)
                .type(MyBean.class)
                .to("direct:remoteService");
        from("direct:remoteService")
                .routeId("direct-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .transform().simple("Hello ${in.body.name}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
