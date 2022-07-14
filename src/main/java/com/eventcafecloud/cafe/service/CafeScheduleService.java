package com.eventcafecloud.cafe.service;

import com.eventcafecloud.cafe.domain.CafeSchedule;
import com.eventcafecloud.cafe.repository.CafeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeScheduleService {

    private final CafeScheduleRepository cafeScheduleRepository;

    /**
     * 카페별 스케줄 불러오기
     */
    public Page<CafeSchedule> findCafeScheduleByCafeId(Long cafeId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        return cafeScheduleRepository.findAllByCafeId(cafeId, pageable);
    }
}
