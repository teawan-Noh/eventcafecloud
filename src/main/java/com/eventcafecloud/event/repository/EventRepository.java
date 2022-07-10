package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findTop5ByOrderByCreatedDateDesc();
}
