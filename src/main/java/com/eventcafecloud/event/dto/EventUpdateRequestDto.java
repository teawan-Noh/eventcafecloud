package com.eventcafecloud.event.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class EventUpdateRequestDto {
    @Length(min = 1,max = 24)
    private String eventName;
    @Length(min = 1)
    private String eventInfo;
}