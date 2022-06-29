package com.eventcafecloud.event.repository;

import com.eventcafecloud.event.domain.EventLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventLikeRepository extends JpaRepository<EventLike,Long> {
    long countByEventEventNumber(Long eventNumber);

    @Query("SELECT el FROM EventLike el " +
            "JOIN User u " +
            "JOIN Event e " +
            "WHERE u.userEmail = :userEmail " +
            "AND e.eventNumber = :eventNumber")
    Optional<EventLike> findEventBookmarkByUserAndEvent(String userEmail, Long eventNumber);
}
