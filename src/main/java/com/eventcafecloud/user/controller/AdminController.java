package com.eventcafecloud.user.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final CafeService cafeService;
    private final EventService eventService;

    @GetMapping("/hosts")
    public String getHostsList(Model model) {
        model.addAttribute("hosts", userService.getHostUserList());
        return "/admin/admin-host";
    }

    @GetMapping("/cafes")
    public String getCafeList(@PageableDefault Pageable pageable, Model model) {
        Page<Cafe> cafeList = cafeService.findAllCafeList(pageable);
        model.addAttribute("cafes", cafeList);
        return "/admin/admin-cafe";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "/admin/admin-user";
    }

    @GetMapping("/events")
    public String getEventsList(@PageableDefault Pageable pageable, Model model) {
        Page<Event> eventList = eventService.findEventList(pageable);
        model.addAttribute("events", eventList);
        return "/admin/admin-event";
    }

    @GetMapping("/posts")
    public String getPostList(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("posts", postService.findAllPostList(pageable));
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
