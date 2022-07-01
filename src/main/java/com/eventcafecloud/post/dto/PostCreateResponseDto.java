package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateResponseDto {
    private String postTitle;
    private String postContent;
    private PostType postType;
}
