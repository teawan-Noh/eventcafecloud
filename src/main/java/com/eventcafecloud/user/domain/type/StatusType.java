package com.eventcafecloud.user.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StatusType {
    ACTIVATE("STATUS_ACTIVATE", "정상"),
    BLOCKED("STATUS_BLOCKED", "신고정지");

    private final String code;
    private final String displayName;

    public static StatusType of(String code) {
        return Arrays.stream(StatusType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(ACTIVATE);
    }
}
