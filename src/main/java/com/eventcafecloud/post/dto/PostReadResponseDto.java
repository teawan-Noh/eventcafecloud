package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostReadResponseDto {
    private Long postNumber;
    private String postTitle;
    private String postContent;
    private PostType postType;
    private Long postCount;

}
