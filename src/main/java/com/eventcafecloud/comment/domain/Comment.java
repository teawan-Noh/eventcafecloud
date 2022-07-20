package com.eventcafecloud.comment.domain;
import com.eventcafecloud.comment.dto.CommentCreateRequestDto;
import com.eventcafecloud.comment.dto.CommentUpdateRequestDto;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_number")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    public Comment (CommentCreateRequestDto commentCreateRequestDto) {
        this.commentContent = commentCreateRequestDto.getCommentContent();
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public void updateComment(CommentUpdateRequestDto requestDto) {
        this.commentContent = requestDto.getCommentContent();
    }

    public void addPost(Post post){
        this.post = post;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
