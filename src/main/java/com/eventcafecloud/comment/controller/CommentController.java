package com.eventcafecloud.comment.controller;

import com.eventcafecloud.comment.dto.*;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@RequiredArgsConstructor
@Controller
@Transactional
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;

    @PostMapping("/comment/new")
    public String createComment(@CookieValue(required = false,name = "access_to") String token, @Validated @ModelAttribute CommentCreateRequestDto requestDto, BindingResult bindingResult){
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
            commentService.createComment(requestDto);
            if (bindingResult.hasErrors()) {
                return "post/postDetail";
            } else {
                return "redirect:/post";
            }
        }
        return "redirect:/post/{id}";
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
