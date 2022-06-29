package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCreatRequestDto;
import com.eventcafecloud.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/cafes/new")
    public String cafeCreateForm(Model model){

        model.addAttribute("cafeCreatRequestDto", new CafeCreatRequestDto());

        return "cafe/createCafeForm";
    }
    // @RequestBody @Valid CafeRequestDto requestDto, BindingResult result
    // @RequestBody 넣은 경우 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
    // ajax 호춣이 아니라서 ContentType 지정을 json으로 못함. -> 데이터 타입 에러
    @PostMapping("/cafes")
    public String cafeCreate(CafeCreatRequestDto requestDto, BindingResult result){
        if(result.hasErrors()){
            return "cafe/createCafeForm";
        }
        // service로직에서 userDetails을 활용 할 경우를 고려하여 id가 아닌 userDetails 자체를 넘기.
        cafeService.createCafe(requestDto);

        return "redirect:/";
    }
}
