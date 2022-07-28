package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCreateRequestDto;
import com.eventcafecloud.cafe.dto.CafeDetailResponseDto;
import com.eventcafecloud.cafe.dto.CafeUpdateRequestDto;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    // 카페 등록 페이지 호출
    @Secured("ROLE_HOST")
    @GetMapping("/cafes/registration")
    public String getCreateCafeForm(Model model, User loginUser){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        model.addAttribute("cafeCreateRequestDto", new CafeCreateRequestDto());

        return "cafe/createCafeForm";
    }

    // @RequestBody @Valid CafeRequestDto requestDto, BindingResult result
    // @RequestBody 넣은 경우 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
    // ajax 호춣이 아니라서 ContentType 지정을 json으로 못함. -> 데이터 타입 에러
    // 카페 등록
    @PostMapping("/cafes")
    public String createCafe(@Valid CafeCreateRequestDto requestDto, BindingResult result, User loginUser, Model model){
        if(result.hasErrors()){
            if (loginUser != null) {
                model.addAttribute("userNick", loginUser.getUserNickname());
                model.addAttribute("userId", loginUser.getId());
            }
            return "cafe/createCafeForm";
        }
        cafeService.saveCafe(requestDto, loginUser);

        return "redirect:/";
    }

    // 카페 전체 조회 페이지 호출
    @GetMapping("/cafes/allList")
    public String getCafeListPage(User loginUser, Model model){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }

        return "cafe/cafeList";
    }

    // 카페 상세보기
    @GetMapping("/cafes/{id}/detail")
    public String getCafeDetailPage(@PathVariable Long id, Model model, User loginUser){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        CafeDetailResponseDto cafeDetailResponseDto = cafeService.findCafeByIdForDetail(id, loginUser);

        model.addAttribute("cafeDetailResponseDto", cafeDetailResponseDto);

        return "cafe/cafeDetail";
    }

    // 카페 수정 페이지 호출
    @GetMapping("/cafes/updateForm")
    public String getCafeUpdatePage(Model model, @RequestParam Long id, User loginUser){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());

            CafeUpdateRequestDto CafeUpdateRequestDto = cafeService.findCafeByIdForUpdateForm(id, loginUser);
            if(CafeUpdateRequestDto.getStatusCode() == 500){
                return "error/500";
            }
            model.addAttribute("cafeUpdateRequestDto", CafeUpdateRequestDto);
            model.addAttribute("cafeId", id);

            return "cafe/updateCafeForm";
        } else {
            return "error/500";
        }
    }

    // 카페 수정
    @PostMapping("/cafes/{id}")
    public String updateCafeInfo(@PathVariable Long id, @Valid CafeUpdateRequestDto requestDto, BindingResult result, User loginUser, Model model){
        if(result.hasErrors()){
            if (loginUser != null) {
                model.addAttribute("userNick", loginUser.getUserNickname());
                model.addAttribute("userId", loginUser.getId());
            }
            model.addAttribute("cafeId", id);
            return "cafe/updateCafeForm";
        }
        int statusCode = cafeService.modifyCafe(id, requestDto, loginUser);
        if(statusCode != 200){
            return "error/500";
        }
        return "redirect:/cafes/allList";
    }
}
