package com.eventcafecloud.event.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class EventUpdateResponseDto {
    private String eventName;
    private String eventInfo;
    // private List<MultipartFile> files;

    public EventUpdateResponseDto(String eventName, String eventInfo){
        this.eventName = eventName;
        this.eventInfo = eventInfo;
        // this.files = files;

    }
}