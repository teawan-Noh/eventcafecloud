package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.CafeScheduleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CafeScheduleCreateDto {

    private LocalDate cafeScheduleStartDate;
    private LocalDate cafeScheduleEndDate;
    private String cafeScheduleInfo;
    private CafeScheduleType cafeScheduleType;
    private int cafeSchedulePrice;
}
