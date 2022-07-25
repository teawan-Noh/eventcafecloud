package com.eventcafecloud.event.dto;

import com.eventcafecloud.cafe.domain.CafeReview;
import com.eventcafecloud.event.domain.EventComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCmtResponseDto {
    private Long userNumber;
    private String userNickname;
    private String userImage;
    private Long eventCmtNumber;
    private String eventCmtContent;
    private LocalDateTime createdDate;

    public EventCmtResponseDto(EventComment eventComment){
        userNumber = eventComment.getUser().getId();
        userNickname = eventComment.getUser().getUserNickname();
        userImage = eventComment.getUser().getUserImage();
        eventCmtNumber = eventComment.getId();
        eventCmtContent = eventComment.getEventCmtContent();
        createdDate = eventComment.getCreatedDate();
    }
}
