package com.example.ticketing.event.repository;

import com.example.ticketing.event.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
