package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.EventImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class EventUpdateRequestDto {
    private String eventName;
    private String eventInfo;
   //  private List<MultipartFile> files;
}