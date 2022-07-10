package com.eventcafecloud.comment.controller;

import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment/registration")
    public String createComment(@PathVariable Long postId, User loginUser,
                                @Validated @ModelAttribute CommentCreateRequestDto requestDto,
                                BindingResult bindingResult){
        if (loginUser != null) {
            commentService.createComment(requestDto, postId, loginUser.getUserEmail());
            if (bindingResult.hasErrors()) {
                return "post/postDetail";
            } else {
                return "redirect:/post/" + postId;
            }
        }
        return "redirect:/post/" + postId;
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

    @Transactional(readOnly = true)
    @GetMapping("/comment/registration")
    public String createComment(Model model) {
        model.addAttribute("commentCreateRequestDto", new CommentCreateRequestDto());
        return "post/postDetail";
    }
}
