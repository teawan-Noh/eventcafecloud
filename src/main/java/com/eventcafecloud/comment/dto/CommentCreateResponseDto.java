package com.eventcafecloud.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class CommentCreateResponseDto {
    private String commentContent;
    private Long id;
}
