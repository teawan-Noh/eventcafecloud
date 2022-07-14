package com.eventcafecloud.comment.dto;

import com.eventcafecloud.comment.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentReadResponseDto {
    private Long id;
    private String commentContent;
    private String userNickname;
    private String userImage;
    private LocalDateTime createdDate;
    private Long userId;

    public CommentReadResponseDto(Comment comment) {
        id = comment.getId();
        commentContent = comment.getCommentContent();
        userNickname = comment.getUser().getUserNickname();
        userImage = comment.getUser().getUserImage();
        createdDate = comment.getCreatedDate();
        userId = comment.getUser().getId();
    }
}
