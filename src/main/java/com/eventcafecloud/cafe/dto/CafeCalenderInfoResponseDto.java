package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.event.domain.Event;
import lombok.Data;

// 카페 상세페이지 달력에 이벤트 띄우기
@Data
public class CafeCalenderInfoResponseDto {
    private String title;
    private String start;
    private String end;

    public CafeCalenderInfoResponseDto(Event event) {
        title = event.getEventName();
        start = event.getEventStartDate();
        end = event.getEventEndDate();
    }
}
