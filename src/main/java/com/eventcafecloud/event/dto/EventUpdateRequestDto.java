package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class EventUpdateRequestDto {
    @Length(min = 1,max = 24)
    private String eventName;
    @Length(min = 1)
    private String eventInfo;
}