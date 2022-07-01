package com.eventcafecloud.post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PostImage {

    @Id
    @GeneratedValue
    @Column(name = "post_image_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_number")
    private Post post;

    private String postImageName;
    private String cafeImageUrl;
}
