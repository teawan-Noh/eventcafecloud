package com.eventcafecloud.user.repository;

import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Page<User> findAll(Pageable pageRequest);

    Page<User> findAllByRole(RoleType role, Pageable pageRequest);

}

