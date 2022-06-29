package com.eventcafecloud.post.repository;

import com.eventcafecloud.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
