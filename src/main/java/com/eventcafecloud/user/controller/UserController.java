package com.eventcafecloud.user.controller;

import com.eventcafecloud.common.ApiResponse;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse getUser(){
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByEmail(principal.getUsername());
        //todo orElseThorw에 ENUM 타입 메세지 추가

        return ApiResponse.success("user", user);
    }
}
