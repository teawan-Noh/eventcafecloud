package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class PostCreateRequestDto {
    @Length(min = 1,max = 52)
    private String postTitle;
    @NotBlank(message = "내용을 입력해주세요.")
    private String postContent;
    private PostType postType;

    public PostCreateRequestDto(PostType postType) {
        this.postType = postType;
    }
}
