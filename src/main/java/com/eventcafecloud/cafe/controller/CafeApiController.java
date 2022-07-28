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
            @RequestParam("sortStrategyKey") String sortStrategyKey,
            @RequestParam("sortStrategyValue") String sortStrategyValue
    ) {
        return cafeService.findAllCafeList(page, size, searchVal, sortStrategyKey, sortStrategyValue);
    }

    @GetMapping("/api/cafes/top5")
    public List<CafeListResponseDto> ReadCafeTopFive() {
        return cafeService.findCafeTopFiveList();
    }

    @GetMapping("/api/cafes/calender")
    public List<CafeCalenderInfoResponseDto> ReadCafeEventInfo(@RequestParam Long id) {
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
            @RequestParam("sortStrategyKey") String sortStrategyKey,
            @RequestParam("sortStrategyValue") String sortStrategyValue
    ) {
        return cafeService.findCafeReviewListByCafeId(id, page, size, sortStrategyKey, sortStrategyValue);
    }

    @DeleteMapping("/api/cafes/review/{id}")
    public void deleteCafeReviewByReviewId(@PathVariable Long id) {
        cafeService.removeCafeReviewByReviewId(id);
    }

    // 카페 삭제
    @DeleteMapping("/api/cafes/{id}")
    public String deleteCafe(@PathVariable Long id, User loginUser) {
        return cafeService.removeCafe(id, loginUser);
    }

    @DeleteMapping("/api/admin/cafes/{id}")
    public String deleteCafeByAdmin(@PathVariable Long id) {
        return cafeService.removeCafeByAdmin(id);
    }


    @PostMapping("/api/cafes/{id}/bookmark")
    public void createBookmark(@PathVariable Long id, User loginUser) {
        cafeService.saveCafeBookmark(id, loginUser);
    }

    @DeleteMapping("/api/cafes/{id}/bookmark")
    public void deleteBookmark(@PathVariable Long id, User loginUser) {
        cafeService.removeCafeBookmark(id, loginUser);
    }
}
