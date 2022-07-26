package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.EventComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepository extends JpaRepository<EventComment, Long> {
    Page<EventComment> findAllByEventId(Long eventNumber, Pageable pageable);

}
