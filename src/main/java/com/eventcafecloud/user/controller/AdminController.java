package com.eventcafecloud.user.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.domain.type.PostType;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.type.ApproveType;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.dto.HostUserResponseDto;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.dto.UserResponseDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final UserService userService;
    private final PostService postService;
    private final CafeService cafeService;
    private final EventService eventService;


    @GetMapping("/users")
    public String getUserList(@PageableDefault Pageable pageable, @RequestParam(required = false, value = "roleType") RoleType roleType, Model model) {
        Page<UserResponseDto> userList = userService.findAllUserList(roleType, pageable);
        model.addAttribute("users", userList);
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "admin/admin-user";
    }

    @GetMapping("/hosts")
    public String getHostsList(@PageableDefault Pageable pageable, @RequestParam(required = false, value = "approveType") ApproveType approveType, Model model) {
        Page<HostUserResponseDto> hostList = userService.findAllHostUserList(approveType, pageable);
        model.addAttribute("hosts", hostList);
        return "admin/admin-host";
    }

    @GetMapping("/posts")
    public String getPostList(@PageableDefault Pageable pageable, @RequestParam(required = false, value = "postType") PostType postType, Model model) {
        Page<Post> postList = postService.findAllPostList(postType, pageable);
        model.addAttribute("posts", postList);
        return "admin/admin-post";
    }

    @DeleteMapping("/posts/{postId}/delete")
    public String deletePostFromAdmin(@PathVariable Long postId) {
        postService.removePost(postId);
        return "redirect:/admin/posts";
    }

    @GetMapping("/events")
    public String getEventsList(@PageableDefault Pageable pageable, @RequestParam(required = false, value = "eventCategory") EventCategory eventCategory, Model model) {
        Page<Event> eventList = eventService.findEventList(eventCategory, pageable);
        model.addAttribute("events", eventList);
        return "admin/admin-event";
    }

    @DeleteMapping("/events/{eventId}/delete")
    public String deleteEventFromAdmin(@PathVariable Long eventId) {
        eventService.removeEvent(eventId);
        return "redirect:/admin/events";
    }

    @GetMapping("/cafes")
    public String getCafeList(@PageableDefault Pageable pageable, Model model) {
        Page<Cafe> cafeList = cafeService.findAllCafeList(pageable);
        model.addAttribute("cafes", cafeList);
        return "admin/admin-cafe";
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
