package com.eventcafecloud.post.controller;

import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @PostMapping("/board")
    public String createPost(@Validated @ModelAttribute PostCreateRequestDto requestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "post/createPostForm";
        }
        postService.createPost(requestDto);
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String getPost(Model model) {
        model.addAttribute("posts", postService.getPost());
        return "post/userBoard";
    }

    @PutMapping("/board/{id}")
    @ResponseBody
    public Long updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/board/{id}")
    @ResponseBody
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @GetMapping("/post/create")
    public String postCreateForm(Model model){
        model.addAttribute("postCreateRequestDto", new PostCreateRequestDto());

        return "post/createPostForm";
    }

}
