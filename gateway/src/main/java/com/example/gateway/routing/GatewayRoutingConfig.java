package com.example.gateway.routing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class GatewayRoutingConfig {

    @Value("${event.service.url}")
    private String eventServiceUrl;
    @Value("${purchase.service.url}")
    private String purchaseServiceUrl;
    @Value("${stock.service.url}")
    private String stockServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> stockServiceRoute() {
        return route("stock_service")
                .route(RequestPredicates.path("/api/stock/**"),
                        HandlerFunctions.http(stockServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("stockServiceCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> stockServiceSwaggerRoute() {
        return route("stock_service_swagger")
                .route(RequestPredicates.path("/aggregate/stock-service/v3/api-docs"), HandlerFunctions.http(stockServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("stockServiceSwaggerCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .filter(setPath("/api-docs"))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> purchaseServiceRoute() {
        return route("purchase_service")
                .route(RequestPredicates.path("/api/purchase"),
                        HandlerFunctions.http(purchaseServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("purchaseServiceCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> purchaseServiceSwaggerRoute() {
        return route("purchase_service_swagger")
                .route(RequestPredicates.path("/aggregate/purchase-service/v3/api-docs"), HandlerFunctions.http(purchaseServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("purchaseServiceSwaggerCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute() {
        return route("event_service")
                .route(RequestPredicates.path("/api/event"),
                        HandlerFunctions.http(eventServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("eventServiceCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> eventServiceSwaggerRoute() {
        return route("event_service_swagger")
                .route(RequestPredicates.path("/aggregate/event-service/v3/api-docs"), HandlerFunctions.http(eventServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("eventServiceSwaggerCircuitBreaker",
                        URI.create("forward:/handleServiceFailure")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> handleServiceFailure() {
        return route("handleServiceFailure")
                .GET("/handleServiceFailure", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("The service is currently unavailable. Try again in a few moments."))
                .build();
    }

}
