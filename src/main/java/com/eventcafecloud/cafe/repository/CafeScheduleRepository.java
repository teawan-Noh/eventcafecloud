package com.eventcafecloud.cafe.repository;


import com.eventcafecloud.cafe.domain.CafeSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeScheduleRepository extends JpaRepository<CafeSchedule, Long> {
    Page<CafeSchedule> findAllByCafeId(Long cafeId, Pageable pageable);
}
