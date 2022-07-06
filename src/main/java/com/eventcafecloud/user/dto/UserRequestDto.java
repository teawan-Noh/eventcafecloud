package com.eventcafecloud.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRequestDto {
    private Long userNumber;
    private String userEmail;
    private String userPassword;
    private String userNickname;
    private String userGender;
    private MultipartFile userImage;
    private String userRegPath;
    private String role;
    private String userStats;
}
