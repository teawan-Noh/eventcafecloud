package com.eventcafecloud.event.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부의 접근을 막기 위해
@Setter
@Getter
public class EventReadResponseDto {
    // private Long eventNumber;
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private String eventInfo;
    private Long cafeNumber;
    private String cafeName;
    private Integer cafeZonecode;
    private String cafeAddress;
    private String cafeAddressDetail;
   //  private List<MultipartFile> files;


    public EventReadResponseDto(Event event) {
        // this.eventNumber = event.getId();
        this.eventName = event.getEventName();
        this.eventCategory = event.getEventCategory();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.eventInfo = event.getEventInfo();
        this.cafeNumber = event.getCafe().getId();
        this.cafeName = event.getCafe().getCafeName();
        this.cafeZonecode = event.getCafe().getCafeZonecode();
        this.cafeAddress = event.getCafe().getCafeAddress();
        this.cafeAddressDetail = event.getCafe().getCafeAddressDetail();

        // to do builder 말고 카페 객체를 받와서 카페 정보 조회하기
    }
}
