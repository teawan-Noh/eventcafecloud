package com.eventcafecloud.post.controller;

import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@Transactional
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;
    private final UserService userService;

    //게시글 작성
    @PostMapping("/post/save")
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
    @Transactional(readOnly = true)
    @GetMapping("/post")
    public String getPosts(@CookieValue(required = false, name = "access_token") String token,Model model, HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        Cookie accessToken = Arrays.stream(cookies)
//                .filter(cookie -> cookie.getName().equals("access_token"))
//                .findAny()
//                .orElse(null);
//
//        if(accessToken != null){
//            String userEmail = tokenProvider.getUserEmailByToken(accessToken.getValue());
//            User user = userService.getUserByEmail(userEmail);
//            model.addAttribute("user", user);
//        }

        //분기 ---------

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
                             @PathVariable Long id,PostUpdateRequestDto requestDto,
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
    //삭제 시 redirect or 페이지호출

    //글작성 페이지 호출
    @Transactional(readOnly = true)
    @GetMapping("/post/create")
    public String createPost(Model model) {
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto());
        return "post/createPostForm";
    }

    //게시글 상세페이지
    @Transactional(readOnly = true)
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("posts", post);
        return "post/postDetail";
    }

    @Transactional(readOnly = true)
    @GetMapping("/post/edit/{id}")
    public String updatePost(@PathVariable Long id,Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("postUpdateRequestDto",post);
        return "post/editPostForm";
    }




//    @Transactional(readOnly = true)
//    @GetMapping("/post/edit/{id}")
//    public String postEdit(@PathVariable Long id, Model model) {
//        Post post = postService.findPostById(id);
//        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto();
//        postUpdateRequestDto.setPostContent(post.getPostContent());
//        postUpdateRequestDto.setPostTitle(post.getPostTitle());
//        postUpdateRequestDto.setPostType(post.getPostType());
//        postUpdateRequestDto.setId(post.getId());
//        model.addAttribute("postCreateRequestDto",postUpdateRequestDto);
//        return "post/createPostForm";

//        대강 감오지?모델에 담아서 넘기면 저기 다 담기고
}

//}