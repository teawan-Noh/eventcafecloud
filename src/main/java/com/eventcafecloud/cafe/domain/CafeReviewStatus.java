package com.eventcafecloud.cafe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CafeReviewStatus {
    INVISIBLE("숨김"),
    VISIBLE("활성");

    private final String displayName;
}
