package com.eventcafecloud.user.repository;

import com.eventcafecloud.user.domain.HostUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostUserRepository extends JpaRepository<HostUser, Long> {
}
