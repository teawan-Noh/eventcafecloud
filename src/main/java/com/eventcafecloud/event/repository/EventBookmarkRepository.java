package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.EventBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventBookmarkRepository extends JpaRepository<EventBookmark, Long> {
    long countByEventEventNumber(Long eventNumber);

    @Query("SELECT evb FROM EventBookmark evb " +
            "JOIN User u " +
            "JOIN Event e " +
            "WHERE u.userEmail = :userEmail " +
            "AND e.eventNumber = :eventNumber")
    Optional<EventBookmark> findEventBookmarkByUserAndEvent(String userEmail, Long eventNumber);
}