package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class EventListResponseDto {
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;

    public EventListResponseDto(Event event) {
        this.eventName = event.getEventName();
        this.eventCategory = event.getEventCategory();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
    }
}