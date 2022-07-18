package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CafeDetailResponseDto {

    private Long cafeNumber;
    private String cafeName;
    private int cafeZonecode;
    private String cafeAddress;
    private String cafeAddressDetail;
    // 경도 : x : Longitude
    private Double cafeX;
    // 위도 : y : Latitude
    private Double cafeY;
    private String cafeInfo;
    private String cafeInfoDetail;
    private String cafePrecaution;
    private int cafeWeekdayPrice;
    private int cafeWeekendPrice;
    private String cafeOpenTime;
    private String cafeCloseTime;
    private List<String> cafeOptions;
    private List<String> cafeImgUrls;
    private String cafeAvgStarRate;

    public CafeDetailResponseDto(Cafe cafe) {
        cafeNumber = cafe.getId();
        cafeName = cafe.getCafeName();
        cafeZonecode = cafe.getCafeZonecode();
        cafeAddress = cafe.getCafeAddress();
        cafeAddressDetail = cafe.getCafeAddressDetail();
        cafeX = cafe.getCafeX();
        cafeY = cafe.getCafeY();
        cafeInfo = cafe.getCafeInfo();
        cafeInfoDetail = cafe.getCafeInfoDetail();
        cafePrecaution = cafe.getCafePrecaution();
        cafeWeekdayPrice = cafe.getCafeWeekdayPrice();
        cafeWeekendPrice = cafe.getCafeWeekendPrice();
        cafeOpenTime = cafe.getCafeOpenTime();
        cafeCloseTime = cafe.getCafeCloseTime();
        cafeOptions = cafe.getCafeOptions().stream()
                .map(i -> i.getCafeOptionType().getDisplayName())
                .collect(Collectors.toList());
        cafeImgUrls = cafe.getCafeImages().stream()
                .map(i -> i.getCafeImageUrl())
                .collect(Collectors.toList());
        if(cafe.getCafeReviewScore() == 0){
            cafeAvgStarRate = "0.0";
        }else {
            DecimalFormat df = new DecimalFormat("0.0");
            cafeAvgStarRate = df.format((float) cafe.getCafeReviewScore() / (float) cafe.getCafeReviews().size());
        }
    }
}
