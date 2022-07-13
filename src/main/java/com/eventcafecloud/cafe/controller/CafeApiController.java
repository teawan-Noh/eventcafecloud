package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCalenderInfoResponseDto;
import com.eventcafecloud.cafe.dto.CafeListResponseDto;
import com.eventcafecloud.cafe.dto.CafeReviewRequestDto;
import com.eventcafecloud.cafe.dto.CafeReviewResponseDto;
import com.eventcafecloud.cafe.service.CafeService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;

    @GetMapping("/api/cafes")
    public Page<CafeListResponseDto> ReadAllCafeList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("searchVal") String searchVal,
            @RequestParam("searchStrategy") String searchStrategy
    ){
        return cafeService.findAllCafeList(page, size, searchVal, searchStrategy);
    }

    @GetMapping("/api/cafes/top5")
    public List<CafeListResponseDto> ReadCafeTopFive(){
        return cafeService.findCafeTopFiveList();
    }

    @GetMapping("/api/cafes/calender")
    public List<CafeCalenderInfoResponseDto> ReadCafeEventInfo(@RequestParam Long id){

        return cafeService.findEventListForCalenderByCafeId(id);
    }

    /**
     * Review 등록
     * id = cafeId
     */
    @PostMapping("/api/cafes/{id}/review")
    public void createCafeReview(User loginUser, @PathVariable Long id, CafeReviewRequestDto requestDto) {
        cafeService.saveCafeReview(requestDto, id, loginUser);
    }

    /**
     * 카페별 Review 조회
     * id = cafeId
     */
    @GetMapping("/api/cafes/{id}/review")
    public Page<CafeReviewResponseDto> ReadCafeReviewAllByCafeId(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("searchStrategy") String searchStrategy
    ){
        return cafeService.findCafeReviewListByCafeId(id, page, size, searchStrategy);
    }

    @DeleteMapping("/api/cafes/review/{id}")
    public void deleteCafeReviewByReviewId(@PathVariable Long id){
        cafeService.removeCafeReviewByReviewId(id);
    }

}
