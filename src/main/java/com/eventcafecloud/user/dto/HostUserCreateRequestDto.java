package com.eventcafecloud.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HostUserCreateRequestDto {
    private String userEmail;

    @NotEmpty(message = "사업자 등록증은 필수로 등록해야합니다.")
    private String certificationFile;
}
