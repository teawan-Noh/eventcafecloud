package com.eventcafecloud.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PostUpdateRequestDto {
    private String postContent;
}
