package com.eventcafecloud.post.controller;

import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.comment.dto.CommentCreateRequestDto;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;
    private final UserService userService;
    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/post/registration")
    public String savePost(@CookieValue(required = false, name = "access_token") String token,
                           @Validated @ModelAttribute PostCreateRequestDto requestDto,
                           BindingResult bindingResult) {
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            User user = userRepository.findByUserEmail(userEmail).orElseThrow(() ->
                    new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
            if (bindingResult.hasErrors()) {
                return "post/createPostForm";
            } else {
                postService.createPost(requestDto, user);
                return "redirect:/post";
            }
        }
        return "redirect:/post";
    }

    //게시판 전체 조회
    @GetMapping("/post")
    public String getPosts(@CookieValue(required = false, name = "access_token") String token,
                           Model model) {
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            User user = userService.getUserByEmail(userEmail);
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", postService.getPostList());
        return "post/userBoard";
    }

    @PutMapping("/post/edit/{id}")
    public String updatePost(@CookieValue(required = false, name = "access_token") String token,
                             @PathVariable Long id, PostUpdateRequestDto requestDto,
                             BindingResult bindingResult) {
        if (token != null) {
            if (bindingResult.hasErrors()) {
                return "redirect:/post/";
            } else {
                postService.updatePost(id, requestDto);
                return "redirect:/post/";
            }
        }
        return "redirect:/post/";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/post";
    }

    //글작성 페이지 호출
    @GetMapping("/post/registration")
    public String createPost(Model model) {
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto());
        return "post/createPostForm";
    }

    //게시글 상세페이지 + 댓글 + 조회수
    @GetMapping("/post/{id}")
    public String getPost(@CookieValue(required = false, name = "access_token") String token,
                          @PathVariable Long id, Model model) {
        if (token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            User user = userService.getUserByEmail(userEmail);
            model.addAttribute("user", user);
        }
        Post post = postService.getPostUpdatedCount(id);
        model.addAttribute("comments", commentService.getComment());
        model.addAttribute("post", post);
        model.addAttribute("commentCreateRequestDto", new CommentCreateRequestDto());
        return "post/postDetail";
    }

    @GetMapping("/post/edit/{id}")
    public String updatePost(@PathVariable Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("postUpdateRequestDto", post);
        return "post/editPostForm";
    }
}