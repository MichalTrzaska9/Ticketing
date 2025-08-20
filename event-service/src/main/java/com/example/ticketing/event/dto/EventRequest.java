package com.example.ticketing.event.dto;

import java.math.BigDecimal;

public record EventRequest(String id, String name, String description, String stockCode, BigDecimal price) {
}
