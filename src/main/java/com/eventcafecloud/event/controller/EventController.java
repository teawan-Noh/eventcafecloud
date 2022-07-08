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
        return eventService.findEventList();
    }
}