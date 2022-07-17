package com.eventcafecloud.user.repository;

import com.eventcafecloud.user.domain.HostUser;
import com.eventcafecloud.user.domain.type.ApproveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostUserRepository extends JpaRepository<HostUser, Long> {
    Optional<HostUser> findByUserEmail(String userEmail);

    Page<HostUser> findAllByIsApprove(ApproveType approveType, Pageable pageRequest);
}
