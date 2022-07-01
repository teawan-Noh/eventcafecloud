package com.eventcafecloud.post.service;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static com.eventcafecloud.exception.ExceptionStatus.POST_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostCreateResponseDto createPost(PostCreateRequestDto requestDto) {
        Post post = new Post();
        post.setPostTitle(requestDto.getPostTitle());
        post.setPostContent(requestDto.getPostContent());
        post.setPostType(requestDto.getPostType());
        Post postResult = postRepository.save(post);

        return PostCreateResponseDto.builder()
                .postContent(postResult.getPostContent())
                .postTitle(postResult.getPostTitle())
                .postType(postResult.getPostType())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostReadResponseDto> getPost() {
        List<Post> posts = postRepository.findAll();
        List<PostReadResponseDto> output = new ArrayList<>();

        for (Post post : posts) {
            PostReadResponseDto postReadResponseDto = new PostReadResponseDto();
            postReadResponseDto.setPostTitle(post.getPostTitle());
            postReadResponseDto.setPostContent(post.getPostContent());
            postReadResponseDto.setId(post.getId());
            postReadResponseDto.setPostCount(post.getPostCount());
            output.add(postReadResponseDto);
        }
        return output;
    }

    public Long updatePost(@PathVariable Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        post.updatePost(requestDto);
        return id;
    }

    public Long deletePost(Long id) {
        try {
            postRepository.deleteById(id);
        }catch (Exception e) {
           throw new IllegalArgumentException(POST_NOT_FOUND.getMessage());
        }
        return id;
    }
}
