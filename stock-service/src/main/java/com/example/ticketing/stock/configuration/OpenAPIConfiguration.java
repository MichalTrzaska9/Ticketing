package com.example.ticketing.stock.configuration;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfiguration {

    @Bean
    public OpenAPI eventServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("STOCK SERVICE API")
                        .description("REST API for Stock Service"))
                .externalDocs(new ExternalDocumentation()
                        .url("https://stock-service-dummy-url.com/docs"));
    }
}
