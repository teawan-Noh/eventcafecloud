package com.eventcafecloud.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRequestDto {
    private Long userNumber;
    private String userEmail;
    private String uerPassword;

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String userNickname;
    private String userGender;

    @NotEmpty(message = "이미지 파일을 선택해 주세요.")
    private MultipartFile userImage;
    private String userRegPath;
    private String role;
    private String userStatus;
}
