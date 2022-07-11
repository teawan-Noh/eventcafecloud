package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCreateRequestDto;
import com.eventcafecloud.cafe.dto.CafeDetailResponseDto;
import com.eventcafecloud.cafe.dto.CafeReviewRequestDto;
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
    public String cafeCreateForm(Model model, User loginUser){

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

    // 카페 전체 조회
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
        CafeDetailResponseDto cafeDetailResponseDto = cafeService.findCafeByIdForDetail(id);

        model.addAttribute("cafeDetailResponseDto", cafeDetailResponseDto);

        return "cafe/cafeDetail";
    }

    // 카페 수정 페이지 호출
    @GetMapping("/cafes/updateForm")
    public String getCafeUpdatePage(Model model, @RequestParam Long id, User loginUser){
        if (loginUser != null) {
            model.addAttribute("userNick", loginUser.getUserNickname());
            model.addAttribute("userId", loginUser.getId());
        }
        CafeUpdateRequestDto CafeUpdateRequestDto = cafeService.findCafeByIdForUpdate(id);

        model.addAttribute("cafeUpdateRequestDto", CafeUpdateRequestDto);
        model.addAttribute("cafeId", id);

        return "cafe/updateCafeForm";
    }

    // 카페 수정
    @PostMapping("/cafes/{id}")
    public String updateCafeInfo(@PathVariable Long id, @Valid CafeUpdateRequestDto requestDto, BindingResult result){

        if(result.hasErrors()){
            return "/cafes/updateForm?id="+id;
        }
        cafeService.modifyCafe(id, requestDto);
        return "redirect:/cafes/allList";
    }

    // 카페 삭제
    @DeleteMapping("/cafes/{id}")
    public String deleteCafe(@PathVariable Long id){
        cafeService.removeCafe(id);

        return "redirect:/";
    }
}
