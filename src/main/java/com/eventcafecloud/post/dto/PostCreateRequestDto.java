package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class PostCreateRequestDto {
    private String postTitle;
    private String postContent;
    private PostType postType;


    @Builder
    public PostCreateRequestDto(String postTitle, String postContent, PostType postType) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postType = postType;

    }
}
