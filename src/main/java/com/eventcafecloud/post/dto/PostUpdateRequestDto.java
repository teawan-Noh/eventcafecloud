package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.Post;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostUpdateRequestDto {
    @NotBlank(message = "내용을 입력해주세요.")
    private String postContent;
    private Long postId;

    public static PostUpdateRequestDto toDto(Post post) {
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto();
        requestDto.postContent = post.getPostContent();
        requestDto.postId = post.getId();

        return requestDto;
    }
}
