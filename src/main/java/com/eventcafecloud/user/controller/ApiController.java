package com.eventcafecloud.user.controller;

import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.service.UserService;
import com.eventcafecloud.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    public final static String ACCESS_TOKEN = "access_token";
    private final static String REFRESH_TOKEN = "refresh_token";

    @GetMapping("/userLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

        return "로그아웃 되었습니다.";
    }

    @GetMapping("/user/exists")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@RequestParam("nick") String nick, User loginUser) {
        String loginNick = loginUser.getUserNickname();
        Boolean result;
        if (loginNick.equals(nick)) {
            result = false;
        } else {
            result = userService.checkNicknameDuplicate(nick);
        }
        return ResponseEntity.ok(result);
    }
}
