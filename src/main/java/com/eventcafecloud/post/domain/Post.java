package com.eventcafecloud.post.domain;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.dto.PostUpdateRequestDto;
import com.eventcafecloud.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter

public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column
    private Long postCount;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();


    public void updatePost(PostUpdateRequestDto requestDto) {
        this.postContent = requestDto.getPostContent();
    }

}
