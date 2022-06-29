package com.eventcafecloud.comment.dto;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class CommentCreateRequestDto {
    private Post postNumber;
    private String commentContent;
}
