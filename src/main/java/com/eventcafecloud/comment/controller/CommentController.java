package com.eventcafecloud.comment.controller;

import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public CommentCreateResponseDto createComment(@RequestBody CommentCreateRequestDto requestDto){
        return commentService.createComment(requestDto);
    }

    @Transactional(readOnly = true)
    @GetMapping("/comment")
    @ResponseBody
    public List<CommentReadResponseDto> getComment() {
        return commentService.getComment();
    }

    @PutMapping("/comment/{id}")
    @ResponseBody
    public Long updateComment(@PathVariable Long id, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseBody
    public Long deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }
}
