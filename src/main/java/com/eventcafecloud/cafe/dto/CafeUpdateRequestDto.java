package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeOption;
import com.eventcafecloud.cafe.domain.CafeOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CafeUpdateRequestDto {

    @NotEmpty(message = "카페 이름은 필수 입니다.")
    private String cafeName;

    private int cafeZonecode;

    @NotEmpty(message = "주소 입력은 필수 입니다.")
    private String cafeAddress;

    @NotEmpty(message = "상세 주소 입력은 필수 입니다.")
    private String cafeAddressDetail;

    // 경도 : x : Longitude
    private Double cafeX;
    // 위도 : y : Latitude
    private Double cafeY;

    @NotEmpty(message = "한 줄 소개 입력은 필수 입니다.")
    private String cafeInfo;

    @NotEmpty(message = "상세 소개 입력은 필수 입니다.")
    private String cafeInfoDetail;

    @NotEmpty(message = "주의 사항 입력은 필수 입니다.")
    private String cafePrecaution;

    @NotNull(message = "평일 요금 입력은 필수 입니다.")
    private int cafeWeekdayPrice;

    @NotNull(message = "주말 요금 입력은 필수 입니다.")
    private int cafeWeekendPrice;

    private String cafeOpenTime;
    private String cafeCloseTime;

    private List<CafeOptionType> cafeOptions;
    private List<MultipartFile> files;

    private int statusCode;

    // update의 경우 dto를 양방향으로 사용하게 되는데 생성자로 만들경우 수정 요청시 파라미터에 호출될 떄 생성자가 호출되어 값을 넣어주지 못하여 null 관련 에러 발생
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
        requestDto.statusCode = 200;

        // 보내기는 했는데 타임리프로 체크박스 체크 방법을 찾지 못함
//        for (int i = 0; i < cafe.getCafeOptions().size(); i++) {
//            System.out.println(cafe.getCafeOptions().get(i).getCafeOptionType());
//            requestDto.cafeOptions.add(cafe.getCafeOptions().get(i).getCafeOptionType());
//        }

        return requestDto;
    }
}
