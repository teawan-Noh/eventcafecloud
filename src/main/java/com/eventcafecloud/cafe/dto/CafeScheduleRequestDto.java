package com.eventcafecloud.cafe.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CafeScheduleRequestDto {

    @NotEmpty(message = "시작일을 입력해주세요")
    private String cafeScheduleStartDate;

    @NotEmpty(message = "종료일 입력해주세요")
    private String cafeScheduleEndDate;
    private String cafeScheduleInfo;
}
