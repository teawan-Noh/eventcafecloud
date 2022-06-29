package com.eventcafecloud.post.dto;

import com.eventcafecloud.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequestDto {
    private String postContent;
}
