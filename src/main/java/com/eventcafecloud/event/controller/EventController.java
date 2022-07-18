package com.eventcafecloud.event.controller;

import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.dto.CafeScheduleRequestDto;
import com.eventcafecloud.cafe.service.CafeScheduleService;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.dto.EventListResponseDto;
import com.eventcafecloud.event.dto.EventReadResponseDto;
import com.eventcafecloud.event.dto.EventUpdateRequestDto;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final CafeScheduleService cafeScheduleService;
    private final CafeService cafeService;

    // 이벤트 예약 폼
    @Secured({"ROLE_NORMAL", "ROLE_HOST"})
    @GetMapping("/events/registration")
    public String createEventForm(User loginUser, Model model, @RequestParam Long cafeId) throws ParseException {

        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
            model.addAttribute("cafeId", cafeId);
        }

        ArrayList<String> dates = cafeService.AllReservationListByCafe(cafeId);
        model.addAttribute("dates", dates);
        model.addAttribute("cafeName", cafeService.findCafeByIdForDetail(cafeId).getCafeName());
        //휴무일등록시, 등록 정보를 받아올 객체를 넘김
        model.addAttribute("requestDto", new CafeScheduleRequestDto());
        model.addAttribute("eventCreateRequestDto", new EventCreateRequestDto());
        return "event/createEventForm";
    }

    // 이벤트 예약
    @PostMapping("/events")
    public String createEvent(User loginUser, @Validated @ModelAttribute EventCreateRequestDto requestDto, BindingResult result) {

        eventService.saveEvent(requestDto, loginUser);

        if (result.hasErrors()) {
            return "event/createEventForm";
        } else {
            return "redirect:/events";
        }
    }

    // 이벤트 수정 폼
    @GetMapping("/events/{eventNumber}/edit")
    public String updateEventForm(@PathVariable Long eventNumber, Model model){
        model.addAttribute("eventUpdateRequestDto", new EventUpdateRequestDto());
        model.addAttribute("event", eventService.findEventById(eventNumber));
        return "updateEventModal";
    }

    // 이벤트 수정
    @PostMapping("/events/{eventNumber}/detail")
    public String updateEvent(@PathVariable Long eventNumber, @Validated @ModelAttribute EventUpdateRequestDto requestDto, BindingResult result) {
        eventService.modifyEvent(eventNumber, requestDto);
        return "redirect:/events/{eventNumber}/detail";
    }

    // 이벤트 삭제
    @DeleteMapping("/events/{eventNumber}/detail")
    public String deleteEvent(@PathVariable Long eventNumber) {
        boolean result = eventService.isEventCancelAvail(eventNumber);
        if (result == true) {
            eventService.removeEvent(eventNumber);
        } else {
            return "redirect:/events/" + eventNumber + "/detail";
        }
        return "redirect:/events/" + eventNumber + "/detail";
    }

    //이벤트삭제(마이페이지)
    @DeleteMapping("/profile/{userId}/delete/{eventNumber}")
    public String deleteEventFromProfile(@PathVariable Long eventNumber, @PathVariable Long userId) {
        boolean result = eventService.isEventCancelAvail(eventNumber);
        if (result == true) {
            eventService.removeEvent(eventNumber);
        } else {
            return "redirect:/users/profile/" + userId + "/reservation";
        }
        return "redirect:/users/profile/" + userId + "/reservation";
    }

    // 이벤트 리스트 보기
    @GetMapping("/events")
    public String getEventList(@PageableDefault(size = 10) Pageable pageable,
                               @RequestParam(required = false, defaultValue = "", value = "keyword") String keyword,
                               @RequestParam(required = false, value = "eventCategory") EventCategory eventCategory,
                               User loginUser, Model model) {

        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }

        Page<EventListResponseDto> eventListResponseDtos = eventService.toDtoList(keyword, eventCategory, pageable);
        model.addAttribute("eventListResponseDtos", eventListResponseDtos);

        return "event/eventList";
    }

    // 이벤트 상세 + 수정 폼
    @GetMapping("/events/{eventNumber}/detail")
    public String getEventDetail(User loginUser, @PathVariable Long eventNumber, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }

        EventReadResponseDto eventReadResponseDto = eventService.findEvent(eventNumber);
        model.addAttribute("eventReadResponseDto", eventReadResponseDto);
        model.addAttribute("eventUpdateRequestDto", new EventUpdateRequestDto());
        model.addAttribute("event", eventService.findEventById(eventNumber));
        return "event/eventDetail";
    }
}
