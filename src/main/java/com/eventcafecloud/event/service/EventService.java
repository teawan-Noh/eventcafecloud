package com.eventcafecloud.event.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.dto.EventListResponseDto;
import com.eventcafecloud.event.dto.EventUpdateRequestDto;
import com.eventcafecloud.event.dto.EventUpdateResponseDto;
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

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final S3Service s3Service;

    // 전체 이벤트 목록
    public List<EventListResponseDto> findEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventListResponseDto> result = events.stream()
                .map(e  -> new EventListResponseDto(e))
                .collect(Collectors.toList());
        return result;
    }

    // 이벤트 예약
    @Transactional
    public void saveEvent(EventCreateRequestDto requestDto, String email) {

        User user = userRepository.findByUserEmail(email).orElseThrow(
                () -> new NullPointerException("해당 사용자가 존재하지 않습니다.")
        );
        System.out.println(user);

        Cafe cafe = cafeRepository.findById(1L).orElseThrow(
                () -> new NullPointerException("해당 카페가 존재하지 않습니다.")
        );

        Event event = new Event(requestDto);
        event.addCafe(cafe);
        event.addUser(user);

        List<MultipartFile> files = requestDto.getFiles();
        // s3저장 후 url 반환받음
        List<String> eventImageUrlList = s3Service.upload(files, "eventImage");

        // 카페 이미지 생성
        MultipartFile file;
        String eventImageUrl;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            eventImageUrl = eventImageUrlList.get(i);
            EventImage eventImage = new EventImage(file.getOriginalFilename(), eventImageUrl);
            event.addEventImage(eventImage);
        }
        eventRepository.save(event);
    }

    // 이벤트 수정
    @Transactional
    public EventUpdateResponseDto modifyEvent(Long id, EventUpdateRequestDto requestDto) {
        Event event =  eventRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 이벤트가 존재하지 않습니다.")
        );

        event.updateEvent(requestDto);
        EventUpdateResponseDto eventUpdateResponse = new EventUpdateResponseDto(event.getEventName(), event.getEventInfo());
        return eventUpdateResponse;
    }

    // 이벤트 삭제
    public void removeEvent(Long eventNumber) {
        eventRepository.deleteById(eventNumber);
    }
}
