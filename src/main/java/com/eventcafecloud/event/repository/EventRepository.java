package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.event.dto.EventCreateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // List<Event> findAllByEventNumber(Long eventNumber, EventCreateRequestDto requestDto);
}
