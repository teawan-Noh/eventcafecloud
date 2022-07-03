package com.eventcafecloud.event.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/api/events")
    public List<EventListResponseDto> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping("/api/events")
    public void createEvent(@RequestBody EventCreateRequestDto requestDto, User user, Cafe cafe) {
        eventService.createEvent(requestDto, user, cafe);
    }

    @PutMapping("/api/events/{evnetNumber}")
//    public EventUpdateResponseDto updateEvent(@PathVariable Long eventNumber, EventUpdateRequestDto requestDto){
    public EventUpdateResponseDto updateEvent(@PathVariable Long id, EventUpdateRequestDto requestDto){
        return eventService.updateEvent(id, requestDto);
    }

    @DeleteMapping("/api/events/{eventNumber}")
    public Long deleteEvent(@PathVariable Long eventNumber) {
        eventService.deleteEvent(eventNumber);
        return eventNumber;
    }

//    @GetMapping("/api/events/{eventNumber}")
//    public EventReadResponseDto getEvent(@PathVariable Long id) {
//        return eventService.getEvent(id);
//    }
}