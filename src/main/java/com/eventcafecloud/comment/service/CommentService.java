package com.eventcafecloud.comment.service;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.COMMENT_NOT_FOUND;


@RequiredArgsConstructor
@Service
@Transactional

public class CommentService {

    private final CommentRepository commentRepository;

    public CommentCreateResponseDto createComment(CommentCreateRequestDto requestDto) {
        Comment comment = new Comment();
        comment.setCommentContent(requestDto.getCommentContent());
        Comment commentResult = commentRepository.save(comment);

        return CommentCreateResponseDto.builder()
                .commentContent(commentResult.getCommentContent())
                .build();
    }

    public List<CommentReadResponseDto> getComment() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentReadResponseDto> output = new ArrayList<>();

        for (Comment comment : comments) {
            CommentReadResponseDto commentReadResponseDto = new CommentReadResponseDto();
            commentReadResponseDto.setCommentContent(comment.getCommentContent());
            commentReadResponseDto.setCommentNumber(comment.getCommentNumber());
            output.add(commentReadResponseDto);
        }
        return output;
    }

    @Transactional(readOnly = true)
    public CommentUpdateResponseDto updateComment(@PathVariable Long CommentNumber, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(CommentNumber).orElseThrow(() -> new IllegalArgumentException(COMMENT_NOT_FOUND.getMessage()));
        comment.updateComment(requestDto);
        return CommentUpdateResponseDto.builder()
                .commentNumber(comment.getCommentNumber())
                .build();
    }

    public CommentDeleteResponseDto deleteComment(Long commentNumber) {
        CommentDeleteResponseDto commentDeleteResponseDto = new CommentDeleteResponseDto(commentNumber);
        try {
            commentRepository.deleteById(commentNumber);
        } catch (Exception e) {
            throw new IllegalArgumentException(COMMENT_NOT_FOUND.getMessage());
        }
        return commentDeleteResponseDto;
    }

}
