package com.eventcafecloud.event.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부의 접근을 막기 위해
@Setter
@Getter
public class EventReadResponseDto {
    private Long eventNumber;
    private String eventName;
    private EventCategory eventCategory;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String eventInfo;
    private Cafe cafe;
    private List<EventImage> eventImage;


    public EventReadResponseDto(Event event, Cafe cafe) {
        this.eventNumber = event.getEventNumber();
        this.eventName = event.getEventName();
        this.eventCategory = event.getEventCategory();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.eventInfo = event.getEventInfo();
        this.cafe = cafe;

        // to do builder 말고 카페 객체를 받와서 카페 정보 조회하기
    }
}
