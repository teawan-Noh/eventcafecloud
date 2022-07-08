package com.eventcafecloud.event.controller;

import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.dto.EventListResponseDto;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventViewController {

    private final EventService eventService;
    private final AuthTokenProvider tokenProvider;

    // 이벤트 예약 폼
    @GetMapping("/events/new")
    public String createEvent(Model model) {
        model.addAttribute("eventCreateRequestDto", new EventCreateRequestDto());
        return "event/createEventForm";
    }

//    // 이벤트 예약 폼
//    @PostMapping("/events/new")
//    public String createEvent(@CookieValue(required = false, name = "access_token") String token,
//                              @Validated @ModelAttribute EventCreateRequestDto requestDto, BindingResult result) {
//        if (token != null) {
//            String email = tokenProvider.getUserEmailByToken(token);
//            eventService.saveEvent(requestDto, email);
//
//            if (result.hasErrors()) {
//                return "event/createEvnetForm";
//            } else {
//                return "redirect:/events";
//            }
//        }
//        return "redirect:/events";
//    }

    // 이벤트 리스트 보기
    @GetMapping("/events")
    public String list(Model model) {
    List<EventListResponseDto> eventListResponseDtos = eventService.findEvents();
    model.addAttribute("eventListResponseDtos", eventListResponseDtos);
    return "event/eventList";
    }
}
