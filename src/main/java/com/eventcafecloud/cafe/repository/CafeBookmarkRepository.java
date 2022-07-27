package com.eventcafecloud.cafe.repository;

import com.eventcafecloud.cafe.domain.CafeBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeBookmarkRepository extends JpaRepository<CafeBookmark, Long> {
    void deleteByCafeIdAndUserId(Long cafeId, Long loginUserId);

    boolean existsByCafeIdAndUserId(Long cafeId, Long loginUserId);

    Page<CafeBookmark> findAllByUserId(Long userId, Pageable pageable);
}
