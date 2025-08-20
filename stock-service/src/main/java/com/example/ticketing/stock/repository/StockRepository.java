package com.example.ticketing.stock.repository;

import com.example.ticketing.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    boolean existsByStockCodeAndQuantityIsGreaterThanEqual(String stockCode, Integer quantity);

    Optional<Stock> findByStockCode(String stockCode);

}
