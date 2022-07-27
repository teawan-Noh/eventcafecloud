package com.eventcafecloud.event.controller;

import com.eventcafecloud.cafe.dto.CafeReviewRequestDto;
import com.eventcafecloud.cafe.dto.CafeReviewResponseDto;
import com.eventcafecloud.event.dto.*;
import com.eventcafecloud.event.service.EventService;
import com.eventcafecloud.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventApiController {

    private final EventService eventService;

   // 이벤트 TOP 5 가져오기
    @GetMapping("/api/events/top5")
    public List<EventListResponseDto> ReadEventTopFive() {
        return eventService.findEventTopFiveList();
    }

    // 댓글등록
    @PostMapping("/api/events/{eventNumber}/comment")
    public void createEventComment(User loginUser, @PathVariable Long eventNumber, EventCmtRequestDto requestDto) {
        eventService.saveEventComment(requestDto, eventNumber, loginUser);
    }

    // 이벤트별 댓글 조회
    @GetMapping("/api/events/{eventNumber}/comment")
    public Page<EventCmtResponseDto> ReadEventCmtAllByEventNumber(
            @PathVariable Long eventNumber,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortStrategyKey") String sortStrategyKey
    ){
        return eventService.findEventCmtListByEventId(eventNumber, page, size, sortStrategyKey);
    }

    // 댓글 삭제
    @DeleteMapping("/api/events/comment/{id}")
    public void deleteEventCmtByCommentId(@PathVariable Long id, User loginUser) {
        eventService.removeEventCmtByCmtId(id);
    }

    // 북마크 등록
    @PostMapping("/events/{eventNumber}/bookmark")
    public void createBookmark(@PathVariable Long eventNumber, User loginUser){
        eventService.saveEventBookmark(eventNumber, loginUser);
    }

    // 북마크 삭제
    @DeleteMapping("/events/{eventNumber}/bookmark")
    public void deleteBookmark(@PathVariable Long eventNumber, User loginUser){
        eventService.removeEventBookmark(eventNumber, loginUser);
    }
}