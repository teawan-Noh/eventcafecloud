package com.eventcafecloud.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateResponseDto {
    private String commentContent;
    private Long id;
}
