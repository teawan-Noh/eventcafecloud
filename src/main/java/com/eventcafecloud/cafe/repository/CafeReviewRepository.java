package com.eventcafecloud.cafe.repository;

import com.eventcafecloud.cafe.domain.CafeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeReviewRepository extends JpaRepository<CafeReview, Long> {
}
