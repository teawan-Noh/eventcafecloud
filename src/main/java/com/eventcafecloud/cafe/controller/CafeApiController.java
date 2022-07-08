package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.dto.CafeListResponseDto;
import com.eventcafecloud.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;

//    @GetMapping("/api/v1/cafes")
//    public Page<Cafe> ReadAllCafeList(
////            @RequestParam("page") int page,
////            @RequestParam("size") int size,
////            @RequestParam("sortBy") String sortBy,
////            @RequestParam("isAsc") boolean isAsc
//    ){
//        return cafeService.findAllCafeList3();
//    }

//    @GetMapping("/cafes/test12")
//    public Page<Cafe> ReadAllCafeList2(){
//        System.out.println("CafeApiController ReadAllCafeList2 실행");
//        return cafeService.findAllCafeList2();
//    }

    @GetMapping("/api/cafes/top5")
    public List<CafeListResponseDto> ReadCafeTopFive(){
        return cafeService.findCafeTopFiveList();
    }


}
