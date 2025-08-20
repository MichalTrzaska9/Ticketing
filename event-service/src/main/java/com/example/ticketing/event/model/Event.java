package com.example.ticketing.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@Document(value = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    private String id;
    private String name;
    private String description;
    @JsonProperty("stockCode")
    private String stockCode;
    private BigDecimal price;
}
