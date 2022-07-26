package com.eventcafecloud.user.dto;

import com.eventcafecloud.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRequestDto {
    private Long userNumber;
    private String userEmail;
    private String uerPassword;

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Length(min = 3, max = 20)
    private String userNickname;
    private String userGender;

    private MultipartFile userImage;
    private String userRegPath;
    private String role;
    private String userStatus;

    public static UserRequestDto toDto(User user) {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.userNickname = user.getUserNickname();

        return requestDto;
    }


}
