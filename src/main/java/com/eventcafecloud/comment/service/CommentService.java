package com.eventcafecloud.comment.service;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.repository.CommentRepository;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.repository.PostRepository;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eventcafecloud.exception.ExceptionStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createComment(CommentCreateRequestDto requestDto, Long postId, String userEmail) {
        Comment comment = new Comment(requestDto);
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        post.addComment(comment);
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        user.addComment(comment);
        Comment commentResult = commentRepository.save(comment);

        CommentCreateResponseDto.builder()
                .commentContent(commentResult.getCommentContent())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentReadResponseDto> getCommentByPostNumber(Post post) {
        Optional<List<Comment>> comments = commentRepository.findByPost(post);
        List<CommentReadResponseDto> output = new ArrayList<>();

        for (Comment comment : comments.get()) {
            CommentReadResponseDto commentReadResponseDto = new CommentReadResponseDto();
            commentReadResponseDto.setCommentContent(comment.getCommentContent());
            commentReadResponseDto.setId(comment.getId());
            commentReadResponseDto.setUserNickname(comment.getUser().getUserNickname());
            commentReadResponseDto.setUserImage(comment.getUser().getUserImage());
            commentReadResponseDto.setCreatedDate(comment.getCreatedDate());
            commentReadResponseDto.setUserId(comment.getUser().getId());
            output.add(commentReadResponseDto);
        }
        return output;
    }

    @Transactional(readOnly = true)
    public Long updateComment(@PathVariable Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(COMMENT_NOT_FOUND.getMessage()));
        comment.updateComment(requestDto);
        return id;
    }

    public void deleteComment(Long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(COMMENT_NOT_FOUND.getMessage());
        }
    }
}
