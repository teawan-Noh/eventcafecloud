package com.eventcafecloud.post.controller;

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
    private final UserService userService;
    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/posts/registration")
    public String savePost(@Validated @ModelAttribute PostCreateRequestDto requestDto,
                           BindingResult bindingResult, User loginUser) {
        if (loginUser != null) {
            User user = userRepository.findByUserEmail(loginUser.getUserEmail()).orElseThrow(() ->
                    new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
            if (bindingResult.hasErrors()) {
                return "post/createPostForm";
            } else {
                postService.createPost(requestDto, user);
                return "redirect:/posts";
            }
        }
        return "redirect:/posts";
    }

    //게시판 전체 조회
    @GetMapping("/posts")
    public String getPosts(User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        model.addAttribute("posts", postService.getPostList());
        return "post/userBoard";
    }

    @PutMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateRequestDto requestDto,
                             BindingResult bindingResult, User loginUser) {
        if (loginUser != null) {
            if (bindingResult.hasErrors()) {
                return "redirect:/posts/";
            } else {
                postService.updatePost(id, requestDto);
                return "redirect:/posts/"+id;
            }
        }
        return "redirect:/posts/";
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    //글작성 페이지 호출
    @GetMapping("/posts/registration")
    public String createPost(User loginUser, Model model) {
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto());
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        return "post/createPostForm";
    }

    //게시글 상세페이지 + 댓글 조회 + 조회수증가
    @GetMapping("/posts/{id}")
    public String getPost(User loginUser, @PathVariable Long id, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        Post post = postService.getPostUpdatedCount(id);
        model.addAttribute("comments", commentService.getCommentByPostNumber(post));
        model.addAttribute("post", post);
        model.addAttribute("commentCreateRequestDto", new CommentCreateRequestDto());
        return "post/postDetail";
    }

    @GetMapping("/posts/update/{id}")
    public String updatePost(User loginUser, @PathVariable Long id, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        Post post = postService.findPostById(id);
        model.addAttribute("postId",id);
        model.addAttribute("postUpdateRequestDto", post);
        return "post/editPostForm";
    }
    //리팩토링 이후 사용예정
//    @GetMapping("/posts/update/{id}")
//    public String updatePost(User loginUser, @PathVariable Long id, Model model) {
//        if (loginUser != null) {
//            model.addAttribute("userNick", loginUser.getUserNickname());
//            model.addAttribute("userId", loginUser.getId());
//        }
//        PostUpdateRequestDto postUpdateRequestDto = postService.findPostByIdForUpdate(id);
//        model.addAttribute("postUpdateRequestDto", postUpdateRequestDto);
//        return "post/editPostForm";
//    }
}