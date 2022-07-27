package com.eventcafecloud.user.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.dto.CafeCalenderInfoResponseDto;
import com.eventcafecloud.cafe.dto.CafeScheduleRequestDto;
import com.eventcafecloud.cafe.service.CafeScheduleService;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.post.service.PostService;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.dto.UserRequestDto;
import com.eventcafecloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hosts/profile")
public class hostProfileController {

    private final UserService userService;
    private final PostService postService;
    private final CafeService cafeService;
    private final EventService eventService;
    private final CafeScheduleService cafeScheduleService;

    @GetMapping("/{id}/info")
    public String getUserProfileById(@PathVariable Long id, Model model, User loginUser) {
        //프로필 수정시, 수정 한 정보를 담아 올 request 객체를 넘김
        UserRequestDto userRequestDto = userService.findUserForUpdate(id, loginUser);
        model.addAttribute("userRequestDto", userRequestDto);
        //id에 해당하는 유저의 정보를 넘김
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());
        return "profile-host/host-userInfo";
    }


    @PostMapping("/{id}/info/host/update")
    public String updateHostProfile(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserProfile(id, requestDto);
        return "redirect:/hosts/profile/" + id + "/info";
    }

    @GetMapping("/{id}/host/posts")
    public String getUserPostById(@PageableDefault Pageable pageable, @PathVariable Long id, Model model, User loginUser) {
        Page<Post> postList = postService.findPostListByUser(id, pageable);
        model.addAttribute("posts", postList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());

        return "profile-host/host-posts";
    }

    @GetMapping("/{id}/cafes")
    public String getCafeByUserId(@PageableDefault Pageable pageable, @PathVariable Long id, Model model, User loginUser) {
        Page<Cafe> cafeList = cafeService.findCafeListByUserId(id, pageable);
        model.addAttribute("cafes", cafeList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());

        return "profile-host/host-cafes";
    }

    @GetMapping("/{id}/cafes/{cafeId}/schedule")
    public String getReservationByCafe(@PageableDefault Pageable pageable, @PathVariable Long cafeId, Model model, User loginUser) throws ParseException {
        Page<Event> eventList = eventService.findEventListByCafe(cafeId, pageable);
        Page<CafeSchedule> scheduleList = cafeScheduleService.findCafeScheduleByCafeId(cafeId, pageable);
        ArrayList<String> dates = cafeService.AllReservationListByCafe(cafeId);
        model.addAttribute("events", eventList);
        model.addAttribute("schedules", scheduleList);
        model.addAttribute("userNick", loginUser.getUserNickname());
        model.addAttribute("userId", loginUser.getId());
        model.addAttribute("cafeId", cafeId);
        model.addAttribute("dates", dates);
        model.addAttribute("cafeName", cafeService.findCafeByIdForDetail(cafeId, loginUser).getCafeName());
        //휴무일등록시, 등록 정보를 받아올 객체를 넘김
        model.addAttribute("requestDto", new CafeScheduleRequestDto());

        return "profile-host/host-schedule";
    }

    @PostMapping("/{userId}/cafes/{cafeId}/schedule/holiday")
    public String createHoliday(@Valid CafeScheduleRequestDto requestDto, BindingResult result, @PathVariable Long userId, @PathVariable Long cafeId) {
        cafeScheduleService.saveCafeSchedule(requestDto, cafeId);
        return "redirect:/hosts/profile/" + userId + "/cafes/" + cafeId + "/schedule";
    }


    @PostMapping("/{id}/info/update")
    public String updateUserProfile(@PathVariable Long id, UserRequestDto requestDto) {
        userService.modifyUserProfile(id, requestDto);
        return "redirect:/host/profile/" + id + "/info";
    }

    @ResponseBody
    @GetMapping("/api/cafes/calender/schedule")
    public List<CafeCalenderInfoResponseDto> ReadCafeScheduleInfo(@RequestParam Long id) {
        return cafeScheduleService.findScheduleListForCalenderByCafeId(id);
    }

    @DeleteMapping("/{cafeId}/schedule/delete/{scheduleId}")
    public String deleteSchedule(@PathVariable Long cafeId, @PathVariable Long scheduleId, User loginUser) {
        cafeScheduleService.removeSchedule(scheduleId);
        return "redirect:/hosts/profile/" + loginUser.getId() + "/cafes/" + cafeId + "/schedule";
    }
}
