package com.eventcafecloud.user.controller;

import com.eventcafecloud.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthTokenProvider tokenProvider;

    @GetMapping("/")
    public String main(@CookieValue(required = false, name = "access_token") String token, Model model){
        if(token != null) {
            String userEmail = tokenProvider.getUserEmailByToken(token);
            model.addAttribute("userEmail", userEmail);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
