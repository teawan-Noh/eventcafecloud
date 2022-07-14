package com.eventcafecloud.cafe.domain;

import com.eventcafecloud.cafe.dto.CafeScheduleRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_schedule_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    private String cafeScheduleStartDate;
    private String cafeScheduleEndDate;

    private String cafeScheduleInfo;

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public CafeSchedule(CafeScheduleRequestDto requestDto) {
        this.cafeScheduleStartDate = requestDto.getCafeScheduleStartDate();
        this.cafeScheduleEndDate = requestDto.getCafeScheduleEndDate();
        this.cafeScheduleInfo = requestDto.getCafeScheduleInfo();
    }
}
