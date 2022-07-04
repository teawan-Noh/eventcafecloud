package com.eventcafecloud.post.dto;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostReadResponseDto extends BaseTimeEntity {
    private Long id;
    private String postTitle;
    private String postContent;
    private PostType postType;
    private Long postCount;
    private LocalDateTime createdDate;
}
