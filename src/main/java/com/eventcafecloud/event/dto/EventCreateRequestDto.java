package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class EventCreateRequestDto {
    @NotEmpty(message = "이벤트 이름은 필수입니다.")
    private String eventName;
    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;
    private String eventInfo;
    private String eventPrice;
    private List<MultipartFile> files;
    private Long cafeNumber;
}
