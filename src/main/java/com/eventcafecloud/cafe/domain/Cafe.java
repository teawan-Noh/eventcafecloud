package com.eventcafecloud.cafe.domain;


import com.eventcafecloud.cafe.dto.CafeCreateRequestDto;
import com.eventcafecloud.cafe.dto.CafeUpdateRequestDto;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@Builder
//@AllArgsConstructor // builder 사용하려면 선언해줘야 한다.
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른곳에서 생성자 못쓰도록 막아둠
public class Cafe extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_number")
    private Long id;

    @Column(nullable = false)
    private String cafeName;

    private int cafeZonecode;

    @Column(nullable = false)
    private String cafeAddress;

    @Column(nullable = false)
    private String cafeAddressDetail;

    // 경도 : x : Longitude
    private Double cafeX;

    // 위도 : y : Latitude
    private Double cafeY;

    @Column(nullable = false)
    private String cafeInfo;

    @Column(columnDefinition = "TEXT")
    private String cafeInfoDetail;

    @Column(columnDefinition = "TEXT")
    private String cafePrecaution;

    @Column(nullable = false)
    private int cafeWeekdayPrice;

    @Column(nullable = false)
    private int cafeWeekendPrice;

    private String cafeOpenTime;
    private String cafeCloseTime;
    private int cafeReviewScore;

    // 단반향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<CafeOption> cafeOptions = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<CafeImage> cafeImages = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<CafeReview> cafeReviews = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<CafeSchedule> cafeSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<Event> cafeEvents = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private final List<CafeBookmark> cafeBookmarks = new ArrayList<>();

    public Cafe(CafeCreateRequestDto requestDto) {
        this.cafeName = requestDto.getCafeName();
        this.cafeZonecode = requestDto.getCafeZonecode();
        this.cafeAddress = requestDto.getCafeAddress();
        this.cafeAddressDetail = requestDto.getCafeAddressDetail();
        this.cafeX = requestDto.getCafeX();
        this.cafeY = requestDto.getCafeY();
        this.cafeInfo = requestDto.getCafeInfo();
        this.cafeInfoDetail = requestDto.getCafeInfoDetail();
        this.cafePrecaution = requestDto.getCafePrecaution();
        this.cafeWeekdayPrice = requestDto.getCafeWeekdayPrice();
        this.cafeWeekendPrice = requestDto.getCafeWeekendPrice();
        this.cafeOpenTime = requestDto.getCafeOpenTime();
        this.cafeCloseTime = requestDto.getCafeCloseTime();
        this.cafeReviewScore = 0;
    }

    //==연관관계 편의 메서드==//
    public void addUser(User user){
        this.user = user;
    }

    public void addCafeImage(CafeImage cafeImage) {
        cafeImages.add(cafeImage);
        cafeImage.addCafe(this);
    }

    public void addCafeOption(CafeOption cafeOption) {
        cafeOptions.add(cafeOption);
        cafeOption.addCafe(this);
    }

    public void addCafeReview(CafeReview cafeReview) {
        cafeReview.addCafe(this);
        this.cafeReviews.add(cafeReview);
    }

    public void addCafeSchedule(CafeSchedule cafeSchedule) {
        cafeSchedule.addCafe(this);
        this.cafeSchedules.add(cafeSchedule);
    }

    public void updateCafeInfo(CafeUpdateRequestDto requestDto) {
        this.cafeName = requestDto.getCafeName();
        this.cafeZonecode = requestDto.getCafeZonecode();
        this.cafeAddress = requestDto.getCafeAddress();
        this.cafeAddressDetail = requestDto.getCafeAddressDetail();
        this.cafeX = requestDto.getCafeX();
        this.cafeY = requestDto.getCafeY();
        this.cafeInfo = requestDto.getCafeInfo();
        this.cafeInfoDetail = requestDto.getCafeInfoDetail();
        this.cafePrecaution = requestDto.getCafePrecaution();
        this.cafeWeekdayPrice = requestDto.getCafeWeekdayPrice();
        this.cafeWeekendPrice = requestDto.getCafeWeekendPrice();
        this.cafeOpenTime = requestDto.getCafeOpenTime();
        this.cafeCloseTime = requestDto.getCafeCloseTime();
    }

    public void updateCafeReviewScore(Integer newReviewScore) {
        cafeReviewScore = newReviewScore;
    }
}

