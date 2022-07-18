package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.event.domain.Event;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 카페 상세페이지 달력에 이벤트 띄우기
@Data
public class CafeCalenderInfoResponseDto {
    private String title;
    private String start;
    private String end;

    final String DATE_PATTERN = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();

    public CafeCalenderInfoResponseDto(Event event) throws ParseException {
        title = event.getEventName();
        start = event.getEventStartDate();
        end = DateToString(event.getEventEndDate());
    }

    public CafeCalenderInfoResponseDto(CafeSchedule cafeSchedule) throws ParseException {
        title = cafeSchedule.getCafeScheduleInfo();
        start = cafeSchedule.getCafeScheduleStartDate();
        end = DateToString(cafeSchedule.getCafeScheduleEndDate());
    }

    public String DateToString(String targetDate) throws ParseException {
        Date date = sdf.parse(targetDate);
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        end = dateFormat.format(cal.getTime());

        return end;
    }
}
