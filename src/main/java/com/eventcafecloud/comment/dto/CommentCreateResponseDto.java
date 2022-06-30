package com.eventcafecloud.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CommentCreateResponseDto {
    private String commentContent;
    private Long id;
}
