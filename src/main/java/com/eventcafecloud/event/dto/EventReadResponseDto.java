package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부의 접근을 막기 위해
@Setter
@Getter
public class EventReadResponseDto {
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private String eventInfo;
    private boolean isCancel;
    private Long userNumber;
    private String userNickname;
    private Long cafeNumber;
    private String cafeName;
    private Integer cafeZonecode;
    private String cafeAddress;
    private String cafeAddressDetail;
    private double cafeX;
    private double cafeY;
    private List<String> eventImageUrls;


    public EventReadResponseDto(Event event) {
        this.eventName = event.getEventName();
        this.eventCategory = event.getEventCategory();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.eventInfo = event.getEventInfo();
        this.isCancel = eventCancelAvail(event.getEventStartDate());
        this.userNumber = event.getUser().getId();
        this.userNickname = event.getUser().getUserNickname();
        this.cafeNumber = event.getCafe().getId();
        this.cafeName = event.getCafe().getCafeName();
        this.cafeZonecode = event.getCafe().getCafeZonecode();
        this.cafeAddress = event.getCafe().getCafeAddress();
        this.cafeAddressDetail = event.getCafe().getCafeAddressDetail();
        this.cafeX = event.getCafe().getCafeX();
        this.cafeY = event.getCafe().getCafeY();
        eventImageUrls = event.getEventImages().stream()
                        .map(i -> i.getEventImageUrl())
                        .collect(Collectors.toList());
    }

    public boolean eventCancelAvail(String eventStartDate) {
        Date now;
        Calendar cal = java.util.Calendar.getInstance();
        cal.add(Calendar.DATE, +7);
        now = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String deadline = sdf.format(now);

        return eventStartDate.compareTo(deadline) > 0;
    }
}
