package com.eventcafecloud.event.controller;

import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.service.EventService;
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

//    @GetMapping("/api/events")
//    public List<EventListResponseDto> getEvents(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("sortBy") String sortBy,    // 시작 날짜, 이벤트명
//            @RequestParam("isAsc") boolean isAsc
//    ) {
//        page = page - 1;
//        return eventService.getEvents(page, size, sortBy, isAsc);
//    }

    @PostMapping("/api/events")
    public void createEvent(@RequestBody EventCreateRequestDto requestDto) {
        eventService.createEvent(requestDto);
    }

    @PutMapping("/api/events/{id}")
    public EventUpdateResponseDto updateEvent(@PathVariable Long id, @RequestBody EventUpdateRequestDto requestDto){
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