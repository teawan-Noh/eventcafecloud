package com.eventcafecloud.cafe.domain;

import com.eventcafecloud.cafe.dto.CafeReviewRequestDto;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeReview extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "cafe_review_number")
    private Long id;

    @Column(nullable = false)
    private String reviewContent;

    @Column(nullable = false)
    private Integer reviewRating;

    @Enumerated(EnumType.STRING)
    private CafeReviewStatus reviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @Builder
    public CafeReview(CafeReviewRequestDto requestDto) {
        this.reviewContent = requestDto.getReviewContent();
        this.reviewRating = requestDto.getReviewRating();
        this.reviewStatus = CafeReviewStatus.VISIBLE;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
