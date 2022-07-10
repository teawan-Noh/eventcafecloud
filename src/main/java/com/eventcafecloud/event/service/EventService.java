package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.repository.EventImageRepository;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.eventcafecloud.exception.ExceptionStatus.EVENT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final S3Service s3Service;

    // 이벤트 예약
    @Transactional
    public void saveEvent(EventCreateRequestDto requestDto, User securityUser) {

        User user = userRepository.getById(securityUser.getId());

        Cafe cafe = cafeRepository.findById(1L).orElseThrow(
                () -> new NullPointerException("해당 카페가 존재하지 않습니다.")
        );

        Event event = new Event(requestDto);
        System.out.println(event);
        event.addCafe(cafe);
        event.addUser(user);

        List<MultipartFile> files = requestDto.getFiles();
        List<String> eventImageUrls = s3Service.upload(files, "eventImage");

        // 이벤트 이미지 생성
        MultipartFile file;
        String eventImageUrl;

        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            eventImageUrl = eventImageUrls.get(i);
            EventImage eventImage = new EventImage(file.getOriginalFilename(), eventImageUrl);
            System.out.println(eventImage);
            event.addEventImage(eventImage);

        }
        eventRepository.save(event);
    }

    // 이벤트 수정
    @Transactional
    public EventUpdateResponseDto modifyEvent(Long eventNumber, EventUpdateRequestDto requestDto) {
        Event event =  eventRepository.findById(eventNumber).orElseThrow(
                () -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage())
        );

        event.updateEvent(requestDto);
        EventUpdateResponseDto eventUpdateResponse = new EventUpdateResponseDto(event.getEventName(), event.getEventInfo());
        return eventUpdateResponse;
    }

    // 이벤트 삭제
    public void removeEvent(Long eventNumber) {
        eventRepository.deleteById(eventNumber);
    }


    // 전체 이벤트 목록
    public List<EventListResponseDto> findEventList() {
        List<Event> events = eventRepository.findAll();
        List<EventListResponseDto> result = events.stream()
                .map(e  -> new EventListResponseDto(e))
                .collect(Collectors.toList());
        return result;
    }

    // 이벤트 상세
    public EventReadResponseDto findEvent(Long eventNumber) {
        Event event = eventRepository.findById(eventNumber)
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage()));
        EventReadResponseDto eventReadResponseDto = new EventReadResponseDto(event);
        return eventReadResponseDto;
    }

    // 이벤트 넘버로 이벤트 가져오기
    public Event getEventById(Long eventNumber) {
        Event event = eventRepository.findById(eventNumber)
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage()));
        return event;
    }

    // 이벤트 TOP 5
    public List<EventListResponseDto> findEventTopFiveList() {
        List<Event> eventList = eventRepository.findTop5ByOrderByCreatedDateDesc();

        List<EventListResponseDto> eventListResponseDtos = eventList.stream()
                .map(e -> new EventListResponseDto(e))
                .collect(Collectors.toList());
        return eventListResponseDtos;
    }
}
