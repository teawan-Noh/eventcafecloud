package com.eventcafecloud.post.controller;

import com.eventcafecloud.comment.dto.CommentCreateRequestDto;
import com.eventcafecloud.comment.dto.CommentReadResponseDto;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/posts/registration/{postType}")
    public String savePost(@Validated @ModelAttribute PostCreateRequestDto requestDto,
                           BindingResult bindingResult, User loginUser, @PathVariable PostType postType) {
        if (loginUser != null) {
            User user = userRepository.findByUserEmail(loginUser.getUserEmail()).orElseThrow(() ->
                    new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
            if (bindingResult.hasErrors()) {
                return "post/createPostForm";
            } else {
                postService.createPost(requestDto, user, postType);
                if (postType == PostType.NOTICE) {
                    return "redirect:/posts/notice";
                } else {
                    return "redirect:/posts";
                }
            }
        }
        return "redirect:/posts"+postType;
    }

    //유저게시판 전체 조회
    @GetMapping("/posts")
    public String getUserPosts(User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        model.addAttribute("posts", postService.getPostList());
        model.addAttribute("postType", PostType.USERPOST);
        return "post/userBoard";
    }

    //공지게시판 전체 조회
    @GetMapping("/posts/notice")
    public String getNoticePosts(User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        model.addAttribute("posts", postService.getNoticePostList());
        model.addAttribute("postType", PostType.NOTICE);
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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/registration/{postType}")
    public String createPost(User loginUser, Model model,@PathVariable PostType postType) {
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto(postType));
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
            model.addAttribute("postType", postType);
        }
        return "post/createPostForm";
    }

    //게시글 상세페이지 + 댓글 조회 + 조회수증가
    @GetMapping("/posts/{id}")
    public String readPostDetail(User loginUser, @PathVariable Long id, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        PostReadResponseDto postReadResponseDto = postService.getPostUpdatedCount(id);
        List<CommentReadResponseDto> commentByPostNumber = commentService.getCommentsByPostNumber(id);
        model.addAttribute("post", postReadResponseDto);
        model.addAttribute("comments", commentByPostNumber);
        model.addAttribute("commentCreateRequestDto", new CommentCreateRequestDto());
        return "post/postDetail";
    }

    @GetMapping("/posts/update/{id}")
    public String updatePost(User loginUser, @PathVariable Long id, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        PostUpdateRequestDto postUpdateRequestDto = postService.findPostByIdForUpdate(id);
        model.addAttribute("postId",id);
        model.addAttribute("postUpdateRequestDto", postUpdateRequestDto);
        return "post/editPostForm";
    }
}