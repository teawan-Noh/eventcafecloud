package com.eventcafecloud.cafe.repository;

import com.eventcafecloud.cafe.domain.CafeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeReviewRepository extends JpaRepository<CafeReview, Long> {
    Page<CafeReview> findAllByCafeId(Long cafeNumber, Pageable pageable);
}
