package com.eventcafecloud.cafe.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeImage {

    @Id
    @GeneratedValue
    @Column(name = "cafe_image_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    private String cafeOriginImageName;
    private String cafeImageUrl;

    public CafeImage(String cafeOriginImageName, String cafeImageUrl){
        this.cafeOriginImageName = cafeOriginImageName;
        this.cafeImageUrl = cafeImageUrl;
    }

    public void addCafe(Cafe cafe){
        this.cafe = cafe;
    }
}
