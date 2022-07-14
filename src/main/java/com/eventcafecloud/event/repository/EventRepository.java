package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findTop5ByOrderByCreatedDateDesc();
    List<Event> findALLByCafeId(Long id);
    Page<Event> findAllByCafeId(Long cafeId, Pageable pageRequest);
    Page<Event> findAllByUserId(Long userId, Pageable pageRequest);
}
