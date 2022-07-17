package com.eventcafecloud.user.dto;

import com.eventcafecloud.user.domain.HostUser;
import com.eventcafecloud.user.domain.type.ApproveType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HostUserResponseDto {
    private Long host_user_number;
    private String userEmail;
    private String certificationFile;
    private ApproveType isApprove;
    private Long user_number;
    private LocalDateTime created_date;

    public HostUserResponseDto(HostUser hostUser) {
        host_user_number = hostUser.getId();
        userEmail = hostUser.getUserEmail();
        certificationFile = hostUser.getCertificationFile();
        isApprove = hostUser.getIsApprove();
        user_number = hostUser.getUser().getId();
        created_date = hostUser.getCreatedDate();
    }
}
