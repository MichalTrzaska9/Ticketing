package com.example.ticketing.event.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfiguration {

    @Bean
    public OpenAPI eventServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("EVENT SERVICE API")
                        .description("REST API for Event Service"))
                .externalDocs(new ExternalDocumentation()
                        .url("https://event-service-dummy-url.com/docs"));
    }
}
