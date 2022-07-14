package com.eventcafecloud.cafe.dto;

import com.eventcafecloud.cafe.domain.CafeReview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CafeReviewResponseDto {

    private Long userId;
    private String userNickname;
    private String userImage;
    private Long cafeReviewNumber;
    private String cafeReviewContent;
    private Integer cafeReviewRating;
    private LocalDateTime createdDate;

    public CafeReviewResponseDto(CafeReview cafeReview){
        userId = cafeReview.getUser().getId();
        userNickname = cafeReview.getUser().getUserNickname();
        userImage = cafeReview.getUser().getUserImage();
        cafeReviewNumber = cafeReview.getId();
        cafeReviewContent = cafeReview.getCafeReviewContent();
        cafeReviewRating = cafeReview.getCafeReviewRating();
        createdDate = cafeReview.getCreatedDate();
    }
}
