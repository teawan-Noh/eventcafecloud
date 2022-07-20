package com.eventcafecloud.user.controller;

import com.eventcafecloud.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class ApiController {

    public final static String ACCESS_TOKEN = "access_token";
    private final static String REFRESH_TOKEN = "refresh_token";

    @GetMapping("/userLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

        return "로그아웃 되었습니다.";
    }
}
