package com.eventcafecloud.user.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApproveType {
    WAITING("APPROVE_WAITING", "승인대기"),
    PASS("APPROVE_PASS", "승인"),
    FAIL("APPROVE_FAIL", "거절");

    private final String code;
    private final String displayName;
}
