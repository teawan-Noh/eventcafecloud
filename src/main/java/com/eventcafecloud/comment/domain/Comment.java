package com.eventcafecloud.comment.domain;
import com.eventcafecloud.comment.dto.CommentCreateRequestDto;
import com.eventcafecloud.comment.dto.CommentUpdateRequestDto;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_number")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    public Comment (CommentCreateRequestDto commentCreateRequestDto) {
        this.commentContent = commentCreateRequestDto.getCommentContent();
    }

    public void updateComment(CommentUpdateRequestDto requestDto) {
        this.commentContent = requestDto.getCommentContent();
    }

    public void createComment(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
