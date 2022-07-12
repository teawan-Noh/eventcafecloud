package com.eventcafecloud.post.repository;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByPostTypeOrderByIdDesc(PostType postType);
    Page<Post> findAllByUserId(Long userId, Pageable pageRequest);
}
