package com.eventcafecloud.user.dto;

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
    private String role;
    private String status;
}
