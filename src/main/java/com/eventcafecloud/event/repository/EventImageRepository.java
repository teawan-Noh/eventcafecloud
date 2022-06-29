package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
