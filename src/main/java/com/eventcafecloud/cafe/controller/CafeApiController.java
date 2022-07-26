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
            @RequestParam("sortStrategyKey") String sortStrategyKey
    ){
        return cafeService.findAllCafeList(page, size, searchVal, sortStrategyKey);
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
            @RequestParam("sortStrategyKey") String sortStrategyKey
    ){
        return cafeService.findCafeReviewListByCafeId(id, page, size, sortStrategyKey);
    }

    @DeleteMapping("/api/cafes/review/{id}")
    public void deleteCafeReviewByReviewId(@PathVariable Long id){
        cafeService.removeCafeReviewByReviewId(id);
    }

    // 카페 삭제
    @DeleteMapping("/cafes/{id}")
    public String deleteCafe(@PathVariable Long id, User loginUser) {
        return cafeService.removeCafe(id);
    }

    @PostMapping("/cafes/{id}/bookmark")
    public void createBookmark(@PathVariable Long id, User loginUser){
        cafeService.saveCafeBookmark(id, loginUser);
    }

    @DeleteMapping("/cafes/{id}/bookmark")
    public void deleteBookmark(@PathVariable Long id, User loginUser){
        cafeService.removeCafeBookmark(id, loginUser);
    }
}
