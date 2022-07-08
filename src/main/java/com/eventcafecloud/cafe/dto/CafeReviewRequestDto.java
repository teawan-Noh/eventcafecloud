package com.eventcafecloud.cafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeReviewRequestDto {
    private String reviewContent;
    private Integer reviewRating;
}
