package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCreateRequestDto;
import com.eventcafecloud.cafe.dto.CafeReviewRequestDto;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @Secured("ROLE_HOST")
    @GetMapping("/cafes/registration")
    public String cafeCreateForm(Model model, User loginUser){

        model.addAttribute("cafeCreateRequestDto", new CafeCreateRequestDto());
        model.addAttribute("userNick", loginUser.getUserNickname());

        return "cafe/createCafeForm";
    }

    // @RequestBody @Valid CafeRequestDto requestDto, BindingResult result
    // @RequestBody 넣은 경우 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
    // ajax 호춣이 아니라서 ContentType 지정을 json으로 못함. -> 데이터 타입 에러
    @PostMapping("/cafes")
    public String cafeCreate(@Valid CafeCreateRequestDto requestDto, BindingResult result, User loginUser){
        if(result.hasErrors()){
            return "cafe/createCafeForm";
        }
        cafeService.createCafe(requestDto, loginUser);

        return "redirect:/";
    }

    /**
     * 카페리뷰등록
     */
    @PostMapping("/cafes/{id}/registration/review")
    public String createCafeReview(User loginUser, @PathVariable Long id, CafeReviewRequestDto requestDto) {
        cafeService.saveCafeReview(requestDto, id, loginUser);

        return "redirect:/";
    }

    @GetMapping("/cafes/allList")
    public String cafeListPage(User loginUser, Model model){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
        }

        return "cafe/cafeList";
    }
}
