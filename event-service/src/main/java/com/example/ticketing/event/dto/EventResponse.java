package com.example.ticketing.event.dto;

import java.math.BigDecimal;

public record EventResponse(String id, String name, String description, String stockCode, BigDecimal price) {
}
