package com.eventcafecloud.user.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.service.CafeScheduleService;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hosts/profile")
public class hostProfileController {

    private final UserService userService;
    private final CafeService cafeService;
    private final EventService eventService;
    private final CafeScheduleService cafeScheduleService;

    @GetMapping("/{id}/info")
    public String getUserProfileById(@PathVariable Long id, Model model, User loginUser) {
        //프로필 수정시, 수정 한 정보를 담아 올 request 객체를 넘김
        model.addAttribute("userRequestDto", new UserRequestDto());
        //id에 해당하는 유저의 정보를 넘김
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());
        return "/profile-host/host-userInfo";
    }

//    @GetMapping("/{id}/reservation")
//    public String getUserReservationById(@PageableDefault Pageable pageable, @PathVariable Long id, Model model, User loginUser) {
//        Page<Event> eventList = eventService.getEventListByUser(id, pageable);
//        model.addAttribute("events", eventList);
//        model.addAttribute("userNick", loginUser.getUserNickname());
//        model.addAttribute("userId", loginUser.getId());
//
//        return "/profile/profile-reservation";
//    }

    @GetMapping("/{id}/cafes")
    public String getCafeByUserId(@PageableDefault Pageable pageable, @PathVariable Long id, Model model, User loginUser) {
        Page<Cafe> cafeList = cafeService.getCafeListByUserId(id, pageable);
        model.addAttribute("cafes", cafeList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());

        return "/profile-host/host-cafes";
    }

    @GetMapping("/{id}/cafes/{cafeId}/schedule")
    public String getReservationByCafe(@PageableDefault Pageable pageable, @PathVariable Long cafeId, Model model, User loginUser) {
        Page<Event> eventList = eventService.getEventListByCafe(cafeId, pageable);
        Page<CafeSchedule> scheduleList = cafeScheduleService.findCafeScheduleByCafeId(cafeId, pageable);
        model.addAttribute("events", eventList);
        model.addAttribute("schedules", scheduleList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());
        return "/profile-host/host-schedule";
    }

    @PostMapping("/{id}/info/update")
    public String updateUserProfile(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserProfile(id, requestDto);
        return "redirect:/host/profile/" + id + "/info";
    }

}
