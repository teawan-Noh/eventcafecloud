package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PostUpdateRequestDto {
    @NotBlank(message = "내용을 입력해주세요.")
    private String postContent;
    private Long postId;
    private Long userId;

    public static PostUpdateRequestDto toDto(Post post) {
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto();
        requestDto.postContent = post.getPostContent();
        requestDto.postId = post.getId();
        requestDto.userId = post.getUser().getId();

        return requestDto;
    }
}
