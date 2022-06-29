package com.eventcafecloud.user.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    NORMAL("ROLE_NORMAL", "일반회원"),
    HOST("ROLE_HOST", "사장님"),
    ADMIN("ROLE_ADMIN", "관리자"),
    GUEST("ROLE_GUEST", "게스트");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }
}

