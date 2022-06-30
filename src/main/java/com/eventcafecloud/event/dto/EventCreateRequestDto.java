package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class EventCreateRequestDto {
    private String eventName;
    private EventCategory eventCategory;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String eventInfo;
    private int eventPrice;
    private boolean eventCancelAvail;
    private List<MultipartFile> files;
}
