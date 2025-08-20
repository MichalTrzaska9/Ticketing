package com.example.ticketing.event.service;

import com.example.ticketing.event.dto.EventRequest;
import com.example.ticketing.event.dto.EventResponse;
import com.example.ticketing.event.model.Event;
import com.example.ticketing.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    public EventResponse createEvent(EventRequest eventRequest) {
        Event event = Event.builder()
                .name(eventRequest.name())
                .description(eventRequest.description())
                .stockCode(eventRequest.stockCode())
                .price(eventRequest.price())
                .build();
        eventRepository.save(event);
        log.info("Event created successfully");
        return new EventResponse(event.getId(), event.getName(), event.getDescription(), event.getStockCode(), event.getPrice());
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream().map(event -> new EventResponse(event.getId(), event.getName(), event.getDescription(), event.getStockCode(), event.getPrice()))
                .toList();
    }
}