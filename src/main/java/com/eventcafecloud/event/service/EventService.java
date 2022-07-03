package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.repository.EventImageRepository;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;
    private final EventImageRepository eventImageRepository;
    private final S3Service s3Service;

    public List<EventListResponseDto> getEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventListResponseDto> result = events.stream()
                .map(e  -> new EventListResponseDto(e))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void createEvent(EventCreateRequestDto requestDto, User user, Cafe cafe) {
        Event event = new Event(requestDto, user, cafe);
        eventRepository.save(event);
    }

    @Transactional
    public EventUpdateResponseDto updateEvent(Long id, EventUpdateRequestDto request) {
        Event event =  eventRepository.findById(id).orElseThrow(
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
