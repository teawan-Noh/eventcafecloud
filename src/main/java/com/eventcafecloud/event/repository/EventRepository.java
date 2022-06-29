package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // List<Event> findAllByEventNumber(Long eventNumber, EventCreateRequestDto requestDto);
}
