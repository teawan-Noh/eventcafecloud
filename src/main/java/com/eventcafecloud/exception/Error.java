package com.eventcafecloud.exception;

import com.eventcafecloud.user.domain.User;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Error implements ErrorController {
    private String ERROR_TEMPLATES_PATH = "/error/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, User loginUser) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return ERROR_TEMPLATES_PATH + "401";
            }
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return ERROR_TEMPLATES_PATH + "403";
            }
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_TEMPLATES_PATH + "404";
            }
            if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                System.out.println("실행");
                return ERROR_TEMPLATES_PATH + "405";
            }

            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return ERROR_TEMPLATES_PATH + "500";
            }
        }
        return "error";
    }
}
