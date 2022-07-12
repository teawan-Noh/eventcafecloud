package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class PostCreateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String postTitle;
    @NotBlank(message = "내용을 입력해주세요.")
    private String postContent;
    private PostType postType;
    public PostCreateRequestDto(PostType postType) {
        this.postType = postType;
    }
}
