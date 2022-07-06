package com.eventcafecloud.cafe.controller;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;

    @GetMapping("/cafes")
    public Page<Cafe> getAllCafeList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ){
        return cafeService.findAllCafeList(page , size, sortBy, isAsc);
    }

}
