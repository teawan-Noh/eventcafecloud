package com.eventcafecloud.cafe.service;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.dto.CafeScheduleRequestDto;
import com.eventcafecloud.cafe.repository.CafeRepository;
import com.eventcafecloud.cafe.repository.CafeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        pageable = PageRequest.of(page, 5);
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
}
