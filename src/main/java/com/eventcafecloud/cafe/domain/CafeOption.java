package com.eventcafecloud.cafe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_option_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    private CafeOptionType cafeOptionType;

    public CafeOption(CafeOptionType opntion){
        this.cafeOptionType = opntion;
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
