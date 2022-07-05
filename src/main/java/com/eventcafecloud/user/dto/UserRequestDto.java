package com.eventcafecloud.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Long userNumber;
    private String userEmail;
    private String userPassword;
    private String userNickname;
    private String userGender;
    private String userImage;
    private String userRegPath;
    private String role;
    private String userStats;
}
