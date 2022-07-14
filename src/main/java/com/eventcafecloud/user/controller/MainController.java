package com.eventcafecloud.user.controller;

import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.dto.HostUserCreateRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthTokenProvider tokenProvider;
    private final UserService userService;

    @GetMapping("/")
    public String main(User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Secured("ROLE_NORMAL")
    @GetMapping("/hosts/registration")
    public String registerHostForm(User loginUser, Model model) {
        model.addAttribute("hostUserCreateRequestDto", new HostUserCreateRequestDto());
        model.addAttribute("Email", loginUser.getUserEmail());
        model.addAttribute("userNick", loginUser.getUserNickname());
        return "register/register-host";
    }

    @PostMapping("/api/hosts/registration")
    public String saveHostUser(HostUserCreateRequestDto requestDto) {

        userService.saveHostUser(requestDto);

        return "redirect:/";
    }
}
