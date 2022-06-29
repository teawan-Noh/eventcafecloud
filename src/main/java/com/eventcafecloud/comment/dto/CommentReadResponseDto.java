package com.eventcafecloud.comment.dto;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReadResponseDto {
    private Post postNumber;
    private Long commentNumber;
    private String commentContent;

}
