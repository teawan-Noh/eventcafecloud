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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_review_number")
    private Long id;

    @Column(nullable = false)
    private String cafeReviewContent;

    @Column(nullable = false)
    private Integer cafeReviewRating;

    // 신고기능 구현시 사용
//    @Enumerated(EnumType.STRING)
//    private CafeReviewStatus reviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    public CafeReview(CafeReviewRequestDto requestDto) {
        this.cafeReviewContent = requestDto.getReviewContent();
        this.cafeReviewRating = requestDto.getReviewRating();
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
