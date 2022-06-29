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
    @GeneratedValue
    @Column(name = "cafe_option_number")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @Column(nullable = false)
    private String cafeOptionName;

    private String cafeOptionIcon;
}
