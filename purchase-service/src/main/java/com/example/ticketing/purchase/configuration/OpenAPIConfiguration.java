package com.example.ticketing.purchase.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI purchaseServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("PURCHASE SERVICE API")
                        .description("REST API for PURCHASE Service"))
                .externalDocs(new ExternalDocumentation()
                        .url("https://purchase-service-dummy-url.com/docs"));
    }
}
