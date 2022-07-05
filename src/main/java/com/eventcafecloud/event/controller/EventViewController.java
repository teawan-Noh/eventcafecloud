package com.eventcafecloud.event.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.dto.EventListResponseDto;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventViewController {

    private final EventService eventService;

    @GetMapping("/events/new")
    public String createEvent(Model model) {
        model.addAttribute("eventCreateRequestDto", new EventCreateRequestDto());
        return "event/createEventForm";
    }

    @PostMapping("/events/new")
    public String createEvent(@Valid EventCreateRequestDto requestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "event/createEvnetForm";
        }

        eventService.createEvent(requestDto);
        return "redirect:/";
    }

//    @GetMapping("/events")
//    public List<EventListResponseDto> list(Model model) {
//    List<EventListResponseDto> responseDtos = eventService.getEvents();
//    model.addAttribute("responseDtos", responseDtos);
//    return "events/eventList";
//    }
}
