package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CafeListResponseDto {

    private Long cafeNumber;
    private String cafeName;
    private String cafeInfo;
    private int cafeWeekdayPrice;
    private String cafeImgUrl;
    //여러장 보낼 때 사용
//    private List<String> cafeImgUrls;

    public CafeListResponseDto(Cafe cafe) {
        cafeNumber = cafe.getId();
        cafeName = cafe.getCafeName();
        cafeInfo = cafe.getCafeInfo();
        cafeWeekdayPrice = cafe.getCafeWeekdayPrice();
        cafeImgUrl = cafe.getCafeImages().get(0).getCafeImageUrl();
        //여러장 보낼 때 사용
//        cafeImgUrls = cafe.getCafeImages().stream()
//                .map(i -> i.getCafeImageUrl())
//                .collect(Collectors.toList());;
    }
}
