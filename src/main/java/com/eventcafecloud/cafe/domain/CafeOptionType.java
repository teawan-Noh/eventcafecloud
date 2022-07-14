package com.eventcafecloud.cafe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CafeOptionType {
    NOSMOKE("NOSMOKE", "금연"),
    PARK("PARK", "주차가능"),
    FOOD("FOOD", "외부음식 반입가능"),
    PROJECTOR("PROJECTOR", "프로젝터"),
    WIFI("WIFI", "WIFI");

    private final String code;
    private final String displayName;
}