package com.eventcafecloud.user.controller;

import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hosts")
    public String getHostsList(Model model) {
        model.addAttribute("hosts", userService.getHostUserList());
        return "index";
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
