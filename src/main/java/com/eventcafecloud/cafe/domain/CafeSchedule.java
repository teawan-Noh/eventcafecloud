package com.eventcafecloud.cafe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeSchedule {

    @Id
    @GeneratedValue
    @Column(name = "cafe_schedule_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    private LocalDate cafeScheduleStartDate;
    private LocalDate cafeScheduleEndDate;

    private String cafeScheduleInfo;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL은 숫자로 들어감 사용하지 말 것.
    private CafeScheduleType cafeScheduleType;

    private int cafeSchedulePrice;
}
