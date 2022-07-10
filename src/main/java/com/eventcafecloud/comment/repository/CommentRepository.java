package com.eventcafecloud.comment.repository;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPost(Post post);
}
