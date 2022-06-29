package com.eventcafecloud.user.repository;

import com.eventcafecloud.user.domain.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserEmail(String userEmail);
    UserRefreshToken findByUserEmailAndRefreshToken(String userEmail, String refreshToken);
}
