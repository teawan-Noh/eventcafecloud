package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
}
