package com.eventcafecloud.event.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.user.domain.User;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class EventResponseForProfileDto {

    private Long id;
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private Integer eventPrice;
    private boolean isCancel;
    private Cafe cafe;
    private User user;

    public EventResponseForProfileDto(Event event) {
        id = event.getId();
        eventName = event.getEventName();
        eventCategory = event.getEventCategory();
        eventStartDate = event.getEventStartDate();
        eventEndDate = event.getEventEndDate();
        eventPrice = event.getEventPrice();
        isCancel = eventCancelAVail(event.getEventStartDate());
        cafe = event.getCafe();
        user = event.getUser();
    }

    public boolean eventCancelAVail(String eventStartDate) {
        Date now;
        Calendar cal = java.util.Calendar.getInstance();
        cal.add(Calendar.DATE, +7);
        now = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String deadline = sdf.format(now);

        return eventStartDate.compareTo(deadline) > 0;
    }
}
