package com.eventcafecloud.post.controller;

import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @PostMapping("/board")
    @ResponseBody
    public PostCreateResponseDto createPost(@RequestBody PostCreateRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @GetMapping("/board")
    @ResponseBody
    public List<PostReadResponseDto> getPost() {
        return postService.getPost();
    }

    @PutMapping("/board/{postNumber}")
    @ResponseBody
    public PostUpdateResponseDto updatePost(@PathVariable Long postNumber, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.updatePost(postNumber, requestDto);
    }

    @DeleteMapping("/board/{postNumber}")
    @ResponseBody
    public PostDeleteResponseDto deletePost(@PathVariable Long postNumber) {
        return postService.deletePost(postNumber);
    }
}
