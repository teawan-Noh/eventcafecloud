package com.eventcafecloud.user.controller;

import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping("/{id}/info")
    public String getUserProfileById(@PathVariable Long id, Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        model.addAttribute("user", userService.getUserById(id));
        return "myPage";
    }

    @PostMapping("/{id}/info/update")
    public String updateUserProfile(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserProfile(id, requestDto);
        return "redirect:/user/profile/" + id + "/info";
    }
}
