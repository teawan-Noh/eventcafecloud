package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class EventUpdateResponseDto {
    private String eventName;
    private String eventInfo;
    // private List<MultipartFile> files;

    public EventUpdateResponseDto(String eventName, String eventInfo, List<MultipartFile> files){
        this.eventName = eventName;
        this.eventInfo = eventInfo;
        // this.files = files;

    }
}