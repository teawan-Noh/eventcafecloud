package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventBookmark;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.Data;

@Data
public class EventBookmarkResponseDto {
    private Long id;
    private Long eventId;
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private String eventImageUrl;
    private String eventCmtCount;
    private String eventBookmarkCount;

    public EventBookmarkResponseDto(EventBookmark bookmark) {
        id = bookmark.getId();
        eventId = bookmark.getEvent().getId();
        eventName = bookmark.getEvent().getEventName();
        eventCategory = bookmark.getEvent().getEventCategory();
        eventStartDate = bookmark.getEvent().getEventStartDate();
        eventEndDate = bookmark.getEvent().getEventStartDate();
        eventImageUrl = bookmark.getEvent().getEventImages().get(0).getEventImageUrl();
        eventCmtCount = String.valueOf(bookmark.getEvent().getEventComments().size());
        eventBookmarkCount = String.valueOf(bookmark.getEvent().getEventBookmarks().size());
    }
}
