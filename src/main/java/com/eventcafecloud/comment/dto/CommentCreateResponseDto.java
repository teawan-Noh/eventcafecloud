package com.eventcafecloud.comment.dto;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateResponseDto {
    private String commentContent;
    private Post postNumber;

    @Builder
    public CommentCreateResponseDto(String commentContent, Post postNumber) {
        this.commentContent = commentContent;
        this.postNumber = postNumber;

    }
}
