package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.EventBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventBookmarkRepository extends JpaRepository<EventBookmark, Long> {
    void deleteByEventIdAndUserId(Long eventNumber, Long loginUserId);

    boolean existsByEventIdAndUserId(Long eventNumber, Long loginUserId);

    Page<EventBookmark> findAllByUserId(Long userId, Pageable pageable);
}