package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeOptionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CafeUpdateRequestDto {

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
    private List<CafeOptionType> options;
    private List<MultipartFile> files;
//    private List<CafeReview> cafeReviews = new ArrayList<>();

    // 생성자로
//    public CafeUpdateRequestDto(Cafe cafe) {
//        cafeName = cafe.getCafeName();
//        cafeZonecode = cafe.getCafeZonecode();
//        cafeAddress = cafe.getCafeAddress();
//        cafeAddressDetail = cafe.getCafeAddressDetail();
//        cafeX = cafe.getCafeX();
//        cafeY = cafe.getCafeY();
//        cafeInfo = cafe.getCafeInfo();
//        cafeInfoDetail = cafe.getCafeInfoDetail();
//        cafePrecaution = cafe.getCafePrecaution();
//        cafeWeekdayPrice = cafe.getCafeWeekdayPrice();
//        cafeWeekendPrice = cafe.getCafeWeekendPrice();
//        cafeOpenTime = cafe.getCafeOpenTime();
//        cafeCloseTime = cafe.getCafeCloseTime();
//    }

    public static CafeUpdateRequestDto test(Cafe cafe) {
        CafeUpdateRequestDto abc = new CafeUpdateRequestDto();
        abc.cafeName = cafe.getCafeName();
        abc.cafeZonecode = cafe.getCafeZonecode();
        abc.cafeAddress = cafe.getCafeAddress();
        abc.cafeAddressDetail = cafe.getCafeAddressDetail();
        abc.cafeX = cafe.getCafeX();
        abc.cafeY = cafe.getCafeY();
        abc.cafeInfo = cafe.getCafeInfo();
        abc.cafeInfoDetail = cafe.getCafeInfoDetail();
        abc.cafePrecaution = cafe.getCafePrecaution();
        abc.cafeWeekdayPrice = cafe.getCafeWeekdayPrice();
        abc.cafeWeekendPrice = cafe.getCafeWeekendPrice();
        abc.cafeOpenTime = cafe.getCafeOpenTime();
        abc.cafeCloseTime = cafe.getCafeCloseTime();

        return abc;
    }
}
