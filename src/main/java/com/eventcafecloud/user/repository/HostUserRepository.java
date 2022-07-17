package com.eventcafecloud.user.repository;

import com.eventcafecloud.user.domain.HostUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostUserRepository extends JpaRepository<HostUser, Long> {
    Optional<HostUser> findByUserEmail(String userEmail);
}
