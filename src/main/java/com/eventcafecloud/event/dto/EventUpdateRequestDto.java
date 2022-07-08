package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class EventUpdateRequestDto {
    private String eventName;
    private String eventInfo;
    // private List<MultipartFile> files;
}