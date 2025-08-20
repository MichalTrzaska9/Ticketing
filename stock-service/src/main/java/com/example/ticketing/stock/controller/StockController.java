package com.example.ticketing.stock.controller;

import com.example.ticketing.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isAvailable(@RequestParam String stockCode, @RequestParam Integer quantity) {
        return stockService.isAvailable(stockCode, quantity);
    }

    @PutMapping("/reduce")
    @ResponseStatus(HttpStatus.OK)
    public void reduceStock(@RequestParam String stockCode, @RequestParam Integer quantity) {
        stockService.reduceStock(stockCode, quantity);
    }

    @GetMapping("/quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer getQuantity(@RequestParam String stockCode) {
        return stockService.getQuantity(stockCode);
    }

}
