package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostReadResponseDto {
    private Long id;
    private String postTitle;
    private String postContent;
    private PostType postType;
    private int postCount;
    private String userNickname;
    private LocalDateTime createdDate;
}
