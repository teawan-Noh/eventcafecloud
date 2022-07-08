package com.eventcafecloud.cafe.domain;


import com.eventcafecloud.cafe.dto.CafeCreateRequestDto;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@Builder
//@AllArgsConstructor // builder 사용하려면 선언해줘야 한다.
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른곳에서 생성자 못쓰도록 막아둠
public class Cafe extends BaseTimeEntity {

    @Column(name = "cafe_number")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cafeName;

    private int cafeZonecode;

    private String cafeAddress;

    @Column(nullable = false)
    private String cafeAddressDetail;

    // 경도 : x : Longitude
    private Double cafeX;

    // 위도 : y : Latitude
    private Double cafeY;

    private String cafeInfo;

    private String cafeInfoDetail;

    private String cafePrecaution;

    //    @Column(nullable = false)
    private int cafeWeekdayPrice;

    //    @Column(nullable = false)
    private int cafeWeekendPrice;

    // 단반향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<CafeOption> cafeOptions = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<CafeImage> cafeImages = new ArrayList<>();


//    @OneToMany(mappedBy = "cafe")
//    private List<CafeReview> cafeReviews = new ArrayList<>();

//    @OneToMany(mappedBy = "cafe")
//    private List<CafeSchedule> cafeSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "cafe")
    private List<Event> events = new ArrayList<>();

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
    }

    //==연관관계 편의 메서드==//
    public void addUser(User user){
        this.user = user;
    }

    public void addCafeImage(CafeImage cafeImage){
        cafeImages.add(cafeImage);
        cafeImage.addCafe(this);
    }

    public void addCafeOption(CafeOption cafeOption) {
        cafeOptions.add(cafeOption);
        cafeOption.addCafe(this);
    }
}

