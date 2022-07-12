package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.CafeOptionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CafeCreateRequestDto {

    @NotEmpty(message = "카페 이름은 필수 입니다.")
    private String cafeName;

    private Integer cafeZonecode;

    private String cafeAddress;

    @NotEmpty(message = "주소 입력은 필수 입니다.")
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
    private Integer cafeWeekdayPrice;

    @NotNull(message = "주말 요금 입력은 필수 입니다.")
    private Integer cafeWeekendPrice;

    private String cafeOpenTime;

    private String cafeCloseTime;

    @Size(min=3,max=3, message = "이미지 파일을 3장 선택해 주세요.")
    private List<MultipartFile> files;

    @Size(min=1, message = "옵션 선택은 필수 입니다.")
    private List<CafeOptionType> options;

}
