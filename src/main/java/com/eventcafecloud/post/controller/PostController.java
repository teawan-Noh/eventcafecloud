package com.eventcafecloud.post.controller;

import com.eventcafecloud.comment.dto.CommentCreateRequestDto;
import com.eventcafecloud.comment.dto.CommentReadResponseDto;
import com.eventcafecloud.comment.service.CommentService;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/posts/registration/{postType}")
    public String createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto,
                             BindingResult bindingResult, User loginUser, @PathVariable PostType postType) {
        if (loginUser != null) {
            if (bindingResult.hasErrors()) {
                System.out.println("bindingResult = " + bindingResult);
                return "post/createPostForm";
            } else {
                postService.savePost(requestDto, loginUser, postType);
                if (postType == PostType.NOTICE) {
                    return "redirect:/posts/notice";
                } else {
                    return "redirect:/posts";
                }
            }
        }
        return "redirect:/posts"+postType;
    }

    //게시글 수정
    @PutMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateRequestDto requestDto,
                             BindingResult bindingResult, User loginUser) {
        if (loginUser != null) {
            if (bindingResult.hasErrors()) {
                return "redirect:/posts/";
            } else {
                postService.modifyPost(id, requestDto);
                return "redirect:/posts/"+id;
            }
        }
        return "redirect:/posts/";
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.removePost(id);
        return "redirect:/posts";
    }

    // 글작성 페이지 호출
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

    // 게시글 상세페이지 + 댓글 조회 + 조회수 증가
    @GetMapping("/posts/{id}")
    public String getPostDetail(User loginUser, @PathVariable Long id, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
            System.out.println("model = " + model);
        }
        PostReadResponseDto postReadResponseDto = postService.findPostUpdatedCount(id);
        List<CommentReadResponseDto> commentByPostNumber = commentService.readCommentsByPostNumber(id);
        model.addAttribute("post", postReadResponseDto);
        model.addAttribute("comments", commentByPostNumber);
        model.addAttribute("commentCreateRequestDto", new CommentCreateRequestDto());
        return "post/postDetail";
    }

    // PostId별 게시글 수정페이지 호출
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

    // 유저게시판 전체 조회
    @GetMapping("/posts")
    public String getPostList(@PageableDefault Pageable pageable, Model model, User loginUser) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        Page<Post> postList = postService.findPostList(pageable);
        model.addAttribute("postList", postList);
        model.addAttribute("postType", PostType.USERPOST);
        return "post/userBoard";
    }

    // 공지게시판 전체 조회
    @GetMapping("/posts/notice")
    public String getNoticeList(@PageableDefault Pageable pageable , User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        Page<Post> postList = postService.findNoticeList(pageable);
        model.addAttribute("postList", postList);
        model.addAttribute("postType", PostType.NOTICE);
        return "post/userBoard";
    }

}