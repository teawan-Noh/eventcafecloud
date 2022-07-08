package com.eventcafecloud.user.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType {
    GOOGLE("구글"),
    NAVER("네이버"),
    KAKAO("카카오"),
    LOCAL("EC2");

    private final String displayName;
}

