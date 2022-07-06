package com.eventcafecloud.event.controller;

import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 전체 이벤트 목록 가져오기
    @GetMapping("/api/events")
    public List<EventListResponseDto> getEvents() {
        return eventService.findEvents();
    }

    // 이벤트 수정하기
    @PutMapping("/api/events/{id}")
    public EventUpdateResponseDto updateEvent(@PathVariable Long id, @RequestBody EventUpdateRequestDto requestDto){
        return eventService.modifyEvent(id, requestDto);
    }

    // 이벤트 삭제하기
    @DeleteMapping("/api/events/{eventNumber}")
    public Long deleteEvent(@PathVariable Long eventNumber) {
        eventService.removeEvent(eventNumber);
        return eventNumber;
    }
}