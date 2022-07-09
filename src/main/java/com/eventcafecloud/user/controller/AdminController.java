package com.eventcafecloud.user.controller;

import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/hosts")
    public String getHostsList(Model model) {
        model.addAttribute("hosts", userService.getHostUserList());
        return "/admin/admin-host";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "/admin/admin-user";
    }

    @GetMapping("/cafes")
    public String getCafeList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "/admin/admin-cafe";
    }

    @GetMapping("/posts")
    public String getPostList(Model model) {
        model.addAttribute("posts", postService.getPostList());
//      model.addAttribute("postStatusUpdateRequestDto", new PostStatusUpdateRequestDto());
        return "/admin/admin-post";
    }

    @PostMapping("/users/{id}/update")
    public String updateUserRoleAndStatus(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserRoleAndStatus(id, requestDto);
        return "redirect:/admin/users";
    }

    @PostMapping("/hosts/{id}/pass")
    public String updateNormalUserToHostUser(@PathVariable Long id) {
        userService.modifyNormalUserToHostUser(id);
        return "redirect:/admin/hosts";
    }

    @PostMapping("/hosts/{id}/fail")
    public String updateApproveIsFail(@PathVariable Long id) {
        userService.approveIsFail(id);
        return "redirect:/admin/hosts";
    }
}
