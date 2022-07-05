package com.eventcafecloud.post.controller;

import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eventcafecloud.exception.ExceptionStatus.POST_NOT_FOUND;
import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;


@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;

    @PostMapping("/post/new")
    public String createPost(@CookieValue(required = false, name = "access_token") String token, @Validated @ModelAttribute PostCreateRequestDto requestDto, BindingResult bindingResult, Model model) {
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
            model.addAttribute("userEmail", userEmail);
            postService.createPost(requestDto, user);
            if (bindingResult.hasErrors()) {
                return "post/createPostForm";
            } else {
                return "redirect:/post";
            }
        }
        return "redirect:/post";
    }

    @GetMapping("/post")
    public String getPosts(Model model) {
        model.addAttribute("posts", postService.getPost());
        return "post/userBoard";
    }

    @PutMapping("/post/{id}")
    @ResponseBody
    public Long updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    @ResponseBody
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @GetMapping("/post/create")
    public String postCreate(Model model) {
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto());
        return "post/createPostForm";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("posts", post);
        return "post/postDetail";
    }
}
