package com.eventcafecloud.comment.controller;

import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
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
    private final AuthTokenProvider tokenProvider;

    @PostMapping("/{postId}/comment/registration")
    public String createComment(@PathVariable Long postId,
                                @CookieValue(required = false,name = "access_token") String token,
                                @Validated @ModelAttribute CommentCreateRequestDto requestDto,
                                BindingResult bindingResult){
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            commentService.createComment(requestDto, postId, userEmail);
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
