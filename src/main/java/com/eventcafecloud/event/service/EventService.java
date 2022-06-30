package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;

    public List<EventReadResponseDto> getEvents() {
        List<Event> event = eventRepository.findAll();
        List<EventReadResponseDto> result = new ArrayList<>();
        // TODO 수정필요
        Cafe cafe = cafeRepository.getById(1L);

        for (int i = 0; i < event.size(); i++) {
            // TODO 수정필요
            EventReadResponseDto eventReadResponseDto = new EventReadResponseDto(event.get(i), cafe);
            result.add(eventReadResponseDto);
        }
        return result;
    }

    @Transactional
    public EventCreateResponseDto createEvent(EventCreateRequestDto requestDto, User user) {
        Long cafeNumber = requestDto.getCafeNumber();
        Cafe cafe = cafeRepository.getById(cafeNumber);
        Event event = new Event(requestDto, user, cafe);
        Event eventResult = eventRepository.save(event);
//        return EventCreateResponseDto.from(eventResult);
        return null;
    }

    @Transactional
    public EventUpdateResponseDto updateEvent(Long eventNumber, EventUpdateRequestDto request) {
        Event event =  eventRepository.findById(eventNumber).orElseThrow(
                () -> new NullPointerException("해당 이벤트가 존재하지 않습니다.")
        );

        event.updateEvent(request);
        EventUpdateResponseDto eventUpdateResponse = new EventUpdateResponseDto(event.getEventName(), event.getEventInfo());
        return eventUpdateResponse;
    }

    // 이벤트 삭제
    public void deleteEvent(Long eventNumber) {
        eventRepository.deleteById(eventNumber);
    }
}
