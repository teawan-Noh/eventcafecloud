package com.eventcafecloud.post.dto;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostReadResponseDto {
    private Long id;
    private String userEmail;
    private String postTitle;
    private String postContent;
    private PostType postType;
    private Long userId;
    private int postCount;
    private String userNickname;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostReadResponseDto(Post post) {
        id = post.getId();
        userEmail = post.getPostContent();
        postTitle = post.getPostTitle();
        postContent = post.getPostContent();
        postType = post.getPostType();
        userId = post.getUser().getId();
        postCount = post.getPostCount();
        userNickname = post.getUser().getUserNickname();
        createdDate = post.getCreatedDate();
        modifiedDate = post.getModifiedDate();

    }
}
