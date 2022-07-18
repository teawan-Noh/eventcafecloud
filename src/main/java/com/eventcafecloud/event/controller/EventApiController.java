package com.eventcafecloud.event.controller;

import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventApiController {

    private final EventService eventService;

   // 이벤트 TOP 5 가져오기
    @GetMapping("/api/events/top5")
    public List<EventListResponseDto> ReadEventTopFive() {
        return eventService.findEventTopFiveList();
    }
}