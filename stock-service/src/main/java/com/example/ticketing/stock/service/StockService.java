package com.example.ticketing.stock.service;

import com.example.ticketing.stock.model.Stock;
import com.example.ticketing.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public boolean isAvailable(String stockCode, Integer quantity) {
        return stockRepository.existsByStockCodeAndQuantityIsGreaterThanEqual(stockCode, quantity);
    }

    @Transactional
    public void reduceStock(String stockCode, Integer quantity) {
        Stock stock = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + stockCode));

        if (stock.getQuantity() < quantity) {
            throw new IllegalStateException("Not enough stock for code: " + stockCode);
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }

    public Integer getQuantity(String stockCode) {
        return stockRepository.findByStockCode(stockCode)
                .map(Stock::getQuantity)
                .orElse(0);
    }


}
