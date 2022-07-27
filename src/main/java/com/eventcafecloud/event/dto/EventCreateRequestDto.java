package com.eventcafecloud.event.dto;

import com.eventcafecloud.event.domain.type.EventCategory;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EventCreateRequestDto {
    @NotEmpty(message = "이벤트 이름은 필수입니다.")
    @Length(min=1, max=22)
    private String eventName;

    private EventCategory eventCategory;
    private String eventStartDate;
    private String eventEndDate;

    @NotEmpty(message = "소개 입력은 필수 입니다.")
    private String eventInfo;

    @Min(value = 1000, message = "이벤트 기간을 확인해 주세요")
    private Integer eventPrice;

    @Size(min=1,max=4, message = "이미지 파일을 선택해 주세요.")
    private List<MultipartFile> files;
    private Long cafeNumber;
}
