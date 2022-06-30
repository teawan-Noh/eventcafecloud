package com.eventcafecloud.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateRequestDto {
    private Long id;
    private String commentContent;
}
