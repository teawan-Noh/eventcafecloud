package com.eventcafecloud.user.controller;

import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/profile")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}/info")
    public String getUserProfileById(@PathVariable Long id, Model model, User loginUser) {
        //프로필 수정시, 수정 한 정보를 담아 올 request 객체를 넘김
        model.addAttribute("userRequestDto", new UserRequestDto());
        //id에 해당하는 유저의 정보를 넘김
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());

        return "/profile/profile-userInfo";
    }

    @GetMapping("/{id}/posts")
    public String getUserPostById(@PageableDefault Pageable pageable, @PathVariable Long id, Model model, User loginUser) {
        Page<Post> postList = postService.findPostListByUser(id, pageable);
        model.addAttribute("posts", postList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());

        return "/profile/profile-posts";
    }

    @PostMapping("/{id}/info/update")
    public String updateUserProfile(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserProfile(id, requestDto);
        return "redirect:/users/profile/" + id + "/info";
    }
}
