package com.capstone.productcatalogservice.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${swagger.url}")
    private String swaggerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl(swaggerUrl);
        server.setDescription("Server URL");
        return new OpenAPI()
                .info(new Info()
                        .title("Capstone Project APIs")
                        .version("1.0")
                        .description("This is a sample Spring Boot RESTful service using springdoc-openapi-starter-webmvc-ui."))
                .servers(List.of(server));
    }
}