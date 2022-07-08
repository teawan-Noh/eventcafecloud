package com.eventcafecloud.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentReadResponseDto {
    private Long id;
    private String commentContent;
    private String userNickname;
    private LocalDateTime createdDate;

}
