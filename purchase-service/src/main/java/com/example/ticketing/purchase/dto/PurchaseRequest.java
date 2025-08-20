package com.example.ticketing.purchase.dto;

import java.math.BigDecimal;

public record PurchaseRequest(Long id, String purchaseNumber, String stockCode, BigDecimal price,
                              Integer quantity, UserDetails userDetails) {
}
