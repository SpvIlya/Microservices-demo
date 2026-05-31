package app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI userServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("REST API для управления пользователями с поддержкой HATEOAS")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("https://api.example.com").description("Production server")
                ));
    }
}
