package com.eventcafecloud.user.dto;

import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.domain.type.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserResponseDto {
    private Long userNumber;
    private String userEmail;
    private String userNickname;
    private String userRegPath;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private RoleType role;
    private StatusType status;

    public UserResponseDto(User user) {
        userNumber = user.getId();
        userEmail = user.getUserEmail();
        userNickname = user.getUserNickname();
        userRegPath = user.getUserRegPath().getDisplayName();
        createdDate = user.getCreatedDate();
        modifiedDate = user.getModifiedDate();
        role = user.getRole();
        status = user.getUserStatus();
    }
}
