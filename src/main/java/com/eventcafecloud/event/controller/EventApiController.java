package com.eventcafecloud.event.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class EventApiController {

    private final EventService eventService;

    @PostMapping("/events")
    public String createEvent(@RequestBody EventCreateRequestDto requestDto, User user, Cafe cafe) {
        eventService.createEvent(requestDto, user, cafe);
        return "/event/createEventForm";
    }
}
