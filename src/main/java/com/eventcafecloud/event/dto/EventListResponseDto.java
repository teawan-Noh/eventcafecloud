package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
@Getter
public class EventListResponseDto {
    private Long eventNumber;
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private List<String> eventImageUrls;

    public EventListResponseDto(Event event) {
        this.eventNumber = event.getId();
        this.eventName = event.getEventName();
        this.eventCategory = event.getEventCategory();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        eventImageUrls = event.getEventImages().stream()
                .map(i -> i.getEventImageUrl())
                .collect(Collectors.toList());
    }
}