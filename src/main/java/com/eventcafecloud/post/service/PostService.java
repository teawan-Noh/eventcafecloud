package com.eventcafecloud.post.service;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.dto.*;
import com.eventcafecloud.post.repository.PostRepository;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void createPost(PostCreateRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostReadResponseDto> getPostList() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<PostReadResponseDto> output = new ArrayList<>();

        for (Post post : posts ) {
            PostReadResponseDto postReadResponseDto = new PostReadResponseDto();
            postReadResponseDto.setPostTitle(post.getPostTitle());
            postReadResponseDto.setUserNickname(post.getUser().getUserNickname());
            postReadResponseDto.setId(post.getId());
            postReadResponseDto.setPostCount(post.getPostCount());
            postReadResponseDto.setPostType(post.getPostType());
            postReadResponseDto.setCreatedDate(post.getCreatedDate());
            output.add(postReadResponseDto);
        }
        return output;
    }

    public void updatePost(@PathVariable Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        post.updatePost(requestDto);
    }

    public Long deletePost(Long id) {
        try {
            postRepository.deleteById(id);
        }catch (Exception e) {
           throw new IllegalArgumentException(POST_NOT_FOUND.getMessage());
        }
        return id;
    }

    //페이징 처리된 게시글 리스트 반환
    public Page<Post> findPostList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0: pageable.getPageNumber() -1, pageable.getPageSize());
        return postRepository.findAll(pageable);
    }

    //게시글 ID로 조회
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
    }
}
