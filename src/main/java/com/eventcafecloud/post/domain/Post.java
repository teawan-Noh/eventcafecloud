package com.eventcafecloud.post.domain;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.dto.PostCreateRequestDto;
import com.eventcafecloud.post.dto.PostUpdateRequestDto;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_number")
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @Column(nullable = false)
    private int postCount;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    public void updatePost(PostUpdateRequestDto requestDto) {
        this.postContent = requestDto.getPostContent();
    }

    public Post (PostCreateRequestDto requestDto,User user){
        this.postTitle = requestDto.getPostTitle();
        this.postContent = requestDto.getPostContent();
        this.user = user;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.addPost(this);
    }

    public void updateCount(){
        this.postCount++;
    }

}
