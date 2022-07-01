package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateRequestDto {
    private String postTitle;
    private String postContent;
    private PostType postType;
}
