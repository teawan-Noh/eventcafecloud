package com.eventcafecloud.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/api/events")
    public List<EventReadResponseDto> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping("/api/events")
    public EventCreateResponseDto createEvent(@RequestBody EventCreateRequestDto requestDto, User user) {
        return eventService.createEvent(requestDto, user);
    }

    @PutMapping("/api/events/{evnetNumber}")
    public EventUpdateResponseDto updateEvent(@PathVariable Long eventNumber, EventUpdateRequestDto requestDto){
        return eventService.updateEvent(eventNumber, requestDto);
    }

    @DeleteMapping("/api/events/{eventNumber}")
    public Long deleteEvent(@PathVariable Long eventNumber) {
        eventService.deleteEvent(eventNumber);
        return eventNumber;
    }
}