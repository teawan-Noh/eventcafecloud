package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;

    public List<EventReadResponse> getEvents() {
        List<Event> event = eventRepository.findAll();
        List<EventReadResponse> result = new ArrayList<>();

        for (int i = 0; i < event.size(); i++) {
            EventReadResponse eventReadResponse = new EventReadResponse(event.get(i));
            result.add(eventReadResponse);
        }
        return result;
    }

    @Transactional
    public EventCreateResponse createEvent(EventCreateRequest request, User user) {
        Long cafeNumber = requestDto.getCafeNumber();
        Cafe cafe = cafeRepository.getById(cafeNumber);
        Event event = new Event(request, user, cafe);
        Event eventResult = eventRepository.save(event);
        return EventCreateResponse.from(eventResult);
    }

    @Transactional
    public EventUpdateResponse updateEvent(Long eventNumber, EventUpdateRequest request) {
        Event event =  eventRepository.findById(eventNumber).orElseThrow(
                () -> new NullPointerException("해당 이벤트가 존재하지 않습니다.")
        );

        event.updateEvent(request);
        EventUpdateResponse eventUpdateResponse = new EventUpdateResponse(event.getEventName(), event.getEventInfo(), event.getEventImage());
        return eventUpdateResponse;
    }

    // 이벤트 삭제
    public void deleteEvent(Long eventNumber) {
        eventRepository.deleteById(eventNumber);
    }
}
