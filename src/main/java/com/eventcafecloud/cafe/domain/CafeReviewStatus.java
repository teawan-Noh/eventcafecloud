package com.eventcafecloud.cafe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
// 신고기능 구현시 사용
@Getter
@AllArgsConstructor
public enum CafeReviewStatus {
    INVISIBLE("숨김"),
    VISIBLE("활성");

    private final String displayName;
}
