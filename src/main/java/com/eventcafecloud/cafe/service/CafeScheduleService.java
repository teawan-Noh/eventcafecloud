package com.eventcafecloud.cafe.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.dto.CafeCalenderInfoResponseDto;
import com.eventcafecloud.cafe.dto.CafeScheduleRequestDto;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.cafe.repository.CafeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeScheduleService {

    private final CafeScheduleRepository cafeScheduleRepository;
    private final CafeRepository cafeRepository;

    /**
     * 카페별 스케줄 불러오기
     */
    public Page<CafeSchedule> findCafeScheduleByCafeId(Long cafeId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3, Sort.Direction.ASC, "cafeScheduleStartDate");
        return cafeScheduleRepository.findAllByCafeId(cafeId, pageable);
    }

    /**
     * 카페일정등록
     */
    @Transactional
    public void saveCafeSchedule(CafeScheduleRequestDto requestDto, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow();
        CafeSchedule cafeSchedule = new CafeSchedule(requestDto);
        cafe.addCafeSchedule(cafeSchedule);

        cafeScheduleRepository.save(cafeSchedule);
    }

    /**
     * 캘린더에 카페 스케줄 불러오기
     */
    public List<CafeCalenderInfoResponseDto> findScheduleListForCalenderByCafeId(Long id) {
        List<CafeSchedule> cafeScheduleList = cafeScheduleRepository.findAllByCafeId(id);

        List<CafeCalenderInfoResponseDto> cafeCalenderInfoResponseDtos = cafeScheduleList.stream()
                .map(e -> new CafeCalenderInfoResponseDto(e))
                .collect(Collectors.toList());
        return cafeCalenderInfoResponseDtos;
    }

    /**
     * 스케줄삭제
     */
    @Transactional
    public void removeSchedule(Long scheduleId) {
        cafeScheduleRepository.deleteById(scheduleId);
    }
}
