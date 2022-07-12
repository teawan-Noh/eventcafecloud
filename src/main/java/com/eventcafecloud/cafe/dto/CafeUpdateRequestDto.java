package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeOptionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    // update의 경우 dto를 양방향으로 사용하게 되는데 생성자로 만들경우 수정 요청시 파라미터에 호출될 떄 생성자가 호출되어 값을 넣어주지 못하여 null 관련 에러 발생
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

    public static CafeUpdateRequestDto toDto(Cafe cafe) {
        CafeUpdateRequestDto requestDto = new CafeUpdateRequestDto();
        requestDto.cafeName = cafe.getCafeName();
        requestDto.cafeZonecode = cafe.getCafeZonecode();
        requestDto.cafeAddress = cafe.getCafeAddress();
        requestDto.cafeAddressDetail = cafe.getCafeAddressDetail();
        requestDto.cafeX = cafe.getCafeX();
        requestDto.cafeY = cafe.getCafeY();
        requestDto.cafeInfo = cafe.getCafeInfo();
        requestDto.cafeInfoDetail = cafe.getCafeInfoDetail();
        requestDto.cafePrecaution = cafe.getCafePrecaution();
        requestDto.cafeWeekdayPrice = cafe.getCafeWeekdayPrice();
        requestDto.cafeWeekendPrice = cafe.getCafeWeekendPrice();
        requestDto.cafeOpenTime = cafe.getCafeOpenTime();
        requestDto.cafeCloseTime = cafe.getCafeCloseTime();

        return requestDto;
    }
}
