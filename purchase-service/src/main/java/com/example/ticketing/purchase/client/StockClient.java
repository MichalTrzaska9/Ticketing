package com.example.ticketing.purchase.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface StockClient {

    Logger log = LoggerFactory.getLogger(StockClient.class);

    String CIRCUIT_BREAKER = "stock";
    String API_STOCK = "/api/stock";
    String API_STOCK_REDUCE = "/api/stock/reduce";

    @GetExchange(API_STOCK)
    @CircuitBreaker(name = CIRCUIT_BREAKER, fallbackMethod = "isAvailableFallback")
    @Retry(name = CIRCUIT_BREAKER)
    boolean isAvailable(@RequestParam("stockCode") String stockCode,
                        @RequestParam("quantity") Integer quantity);

    default boolean isAvailableFallback(String stockCode, Integer quantity, Throwable throwable) {
        log.warn("Fallback: Stock unavailable for code={}, qty={}, reason={}",
                stockCode, quantity, throwable.getMessage());
        return false;
    }

    @PutExchange(API_STOCK_REDUCE)
    @CircuitBreaker(name = CIRCUIT_BREAKER, fallbackMethod = "reduceStockFallback")
    @Retry(name = CIRCUIT_BREAKER)
    void reduceStock(@RequestParam("stockCode") String stockCode,
                     @RequestParam("quantity") Integer quantity);

    default void reduceStockFallback(String stockCode, Integer quantity, Throwable throwable) {
        log.error("Fallback: Could not reduce stock for code={}, qty={}, reason={}",
                stockCode, quantity, throwable.getMessage());
        throw new RuntimeException("Stock reduction failed", throwable);
    }
}
