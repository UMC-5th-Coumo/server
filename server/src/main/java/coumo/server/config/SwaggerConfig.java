package coumo.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

//http://localhost:8080/swagger-ui/index.html#
@Configuration
public class SwaggerConfig {

    @Value("${coumo.openapi.dev-url}")
    private String devUrl;

    @Value("${coumo.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

//        Contact contact = new Contact();
//        contact.setEmail("temp@gmail.com");
//        contact.setName("temp");
//        contact.setUrl("https://www.temp.com");

//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Coumo API")
                .version("1.0")
//                .contact(contact)
                .description("This API exposes endpoints to manage Coumo.").termsOfService("http://localhost:8080/");
//                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}