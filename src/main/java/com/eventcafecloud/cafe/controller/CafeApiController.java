package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeCalenderInfoResponseDto;
import com.eventcafecloud.cafe.dto.CafeListResponseDto;
import com.eventcafecloud.cafe.service.CafeService;
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
            @RequestParam("size") int size
//            @RequestParam("sortBy") String sortBy,
//            @RequestParam("isAsc") boolean isAsc
    ){
        page = page - 1;
        return cafeService.findAllCafeList(page, size);
    }

    @GetMapping("/api/cafes/top5")
    public List<CafeListResponseDto> ReadCafeTopFive(){
        return cafeService.findCafeTopFiveList();
    }


    @GetMapping("/api/cafes/calender")
    public List<CafeCalenderInfoResponseDto> ReadCafeEventInfo(@RequestParam Long id){
        System.out.println(id);

        return cafeService.findCafeEventListForCalender(id);
    }
}
