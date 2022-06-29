package com.eventcafecloud.post.domain;

import com.eventcafecloud.comment.Comment;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNumber;

}
