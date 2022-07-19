package com.eventcafecloud.comment.controller;

import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment/registration")
    public String createComment(@PathVariable Long postId, User loginUser,
                                @Valid @ModelAttribute CommentCreateRequestDto requestDto,
                                BindingResult bindingResult) {
        if (loginUser == null || bindingResult.hasErrors())
            return "redirect:/posts/" + postId;
        commentService.saveComment(requestDto, postId, loginUser.getUserEmail());
        return "redirect:/posts/" + postId;
    }

    @PutMapping("/comment/{id}")
    @ResponseBody
    public Long updateComment(@PathVariable Long id, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @PathVariable Long postId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}
