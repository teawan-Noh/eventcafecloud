package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.repository.EventImageRepository;
import com.eventcafecloud.event.repository.EventRepository;
import com.eventcafecloud.s3.S3Service;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.eventcafecloud.exception.ExceptionStatus.CAFE_NOT_FOUND;
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

        Cafe cafe = cafeRepository.findById(requestDto.getCafeNumber()).orElseThrow(
                () -> new IllegalArgumentException(CAFE_NOT_FOUND.getMessage())
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
            event.addEventImage(eventImage);
        }
        eventRepository.save(event);
    }

    // 이벤트 수정
    @Transactional
    public EventUpdateResponseDto modifyEvent(Long eventNumber, EventUpdateRequestDto requestDto) {
        Event event = eventRepository.findById(eventNumber).orElseThrow(
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
    public Page<EventListResponseDto> toDtoList(String keyword, EventCategory eventCategory, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 9);

        Page<Event> events = null;
        if (eventCategory == null) {
            events = eventRepository.findByEventNameContaining(keyword, pageable);
        } else {
            events = eventRepository.findByEventNameContainingAndEventCategory(keyword, eventCategory, pageable);
        }

        Page<EventListResponseDto> eventListResponseDtos = events.map(e ->
                EventListResponseDto.builder()
                        .eventNumber(e.getId())
                        .eventName(e.getEventName())
                        .eventCategory(e.getEventCategory())
                        .eventStartDate(e.getEventStartDate())
                        .eventEndDate(e.getEventEndDate())
                        .eventImageUrls(e.getEventImages().stream()
                                .map(i -> i.getEventImageUrl())
                                .collect(Collectors.toList()))
                        .build());
        return eventListResponseDtos;
    }

    // 이벤트 상세
    public EventReadResponseDto findEvent(Long eventNumber) {
        Event event = eventRepository.findById(eventNumber)
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND.getMessage()));
        EventReadResponseDto eventReadResponseDto = new EventReadResponseDto(event);
        return eventReadResponseDto;
    }

    // 이벤트 넘버로 이벤트 가져오기
    public Event findEventById(Long eventNumber) {
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

    /**
     * 이벤트목록가져오기(admin)
     */
    public Page<Event> findEventList(EventCategory eventCategory, Pageable pageable) {

        Page<Event> eventList;

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        if (eventCategory == null) {
            eventList = eventRepository.findAll(pageable);
        } else {
            eventList = eventRepository.findAllByEventCategory(eventCategory, pageable);
        }

        return eventList;
    }

    /**
     * 이벤트목록(예약내역)가져오기(myProfile)
     */
    @Transactional
    public Page<EventResponseForProfileDto> findEventListByUser(Long userId, Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.Direction.ASC, "eventStartDate");

        Page<Event> eventList = eventRepository.findAllByUserId(userId, pageable);

        return eventList.map(EventResponseForProfileDto::new);
    }

    /**
     * 이벤트목록(예약내역)가져오기(hostProfile)
     */
    public Page<Event> findEventListByCafe(Long cafeId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3, Sort.Direction.ASC, "eventStartDate");

        return eventRepository.findAllByCafeId(cafeId, pageable);
    }

    /**
     * 이벤트기간과 오늘을 비교해서 취소상태를 변경하는 메소드(백엔드처리)
     */
    public boolean isEventCancelAvail(Long id) {
        Date now;
        Calendar cal = java.util.Calendar.getInstance();
        cal.add(Calendar.DATE, +7);
        now = cal.getTime();

        Event event = eventRepository.getById(id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String deadline = sdf.format(now);

        return event.getEventStartDate().compareTo(deadline) > 0;
    }
}

