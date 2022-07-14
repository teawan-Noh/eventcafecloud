package com.eventcafecloud.post.service;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.dto.PostCreateRequestDto;
import com.eventcafecloud.post.dto.PostReadResponseDto;
import com.eventcafecloud.post.dto.PostUpdateRequestDto;
import com.eventcafecloud.post.repository.PostRepository;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import static com.eventcafecloud.exception.ExceptionStatus.POST_NOT_FOUND;
import static com.eventcafecloud.exception.ExceptionStatus.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시글 작성
    public void createPost(PostCreateRequestDto requestDto, User loginUser, PostType postType) {
        User user = userRepository.findByUserEmail(loginUser.getUserEmail()).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND.getMessage()));
        Post post = new Post(requestDto, user, postType);
        postRepository.save(post);
    }

    //게시글 업데이트
    public void updatePost(@PathVariable Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        post.updatePost(requestDto);
    }

    //게시글 삭제
    public void deletePost(Long id) {
        try {
            postRepository.deleteById(id);
        }catch (Exception e) {
           throw new IllegalArgumentException(POST_NOT_FOUND.getMessage());
        }
    }

    //PostId로 게시글 조회
    public PostUpdateRequestDto findPostByIdForUpdate(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        return PostUpdateRequestDto.toDto(post);
    }

    //게시글 조회 및 조회수 증가
    public PostReadResponseDto getPostUpdatedCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
        post.updateCount();
        return new PostReadResponseDto(post);
    }

    //UserID로 게시글 조회

    /**
     * 사용자에 따른 게시글 가져오기
     */
    @Transactional(readOnly = true)
    public Page<Post> getPostListByUser(Long userId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);

        return postRepository.findAllByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);

        return postRepository.findAll(pageable);
    }

    //유저게시판 게시글 조회
    @Transactional(readOnly = true)
    public Page<Post> findPostList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 20, Sort.Direction.DESC, "id");
        return postRepository.findPostsByPostType(PostType.USERPOST, pageable);
    }

    //공지게시판 게시글 조회
    @Transactional(readOnly = true)
    public Page<Post> findNoticeList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 20 , Sort.Direction.DESC,"id");
        return postRepository.findPostsByPostType(PostType.NOTICE,pageable);
    }
}
