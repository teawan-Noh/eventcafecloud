package com.eventcafecloud.event.domain;

import com.eventcafecloud.cafe.Cafe;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventNumber;

    private String evenName;

    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;

    private String eventInfo;

    private Integer eventPrice;

    private Boolean eventCancelAvail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @OneToMany(mappedBy = "event")
    private List<EventImage> eventImages = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventBookmark> eventBookmarks = new ArrayList<>();
}
