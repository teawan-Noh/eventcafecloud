package com.eventcafecloud.post.domain;

import com.eventcafecloud.common.fileutil.Image;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class PostImage extends Image {
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
