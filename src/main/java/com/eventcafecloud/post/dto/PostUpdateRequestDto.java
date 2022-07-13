package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.Post;
import lombok.Data;

@Data
public class PostUpdateRequestDto {
    private String postContent;
    private Long postId;
    
    public static PostUpdateRequestDto toDto(Post post) {
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto();
        requestDto.postContent = post.getPostContent();
        requestDto.postId = post.getId();

        return requestDto;
    }
}
