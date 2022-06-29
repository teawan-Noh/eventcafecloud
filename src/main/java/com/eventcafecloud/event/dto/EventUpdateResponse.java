package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class EventUpdateResponse {
    private String eventName;
    private String eventInfo;
    private List<EventImage> eventImage;
}