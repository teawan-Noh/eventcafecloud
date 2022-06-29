package com.eventcafecloud.comment.dto;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentUpdateResponseDto {
    private Long commentNumber;
}
