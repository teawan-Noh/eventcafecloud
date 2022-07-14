package com.eventcafecloud.post.repository;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageRequest);
    Page<Post> findPostsByPostType(PostType postType, Pageable pageRequest);

    Page<Post> findAll(Pageable pageRequest);
}
