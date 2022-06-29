package com.eventcafecloud.event.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class EventImageRequestDto {
    List<String> eventImageUrl;
}

