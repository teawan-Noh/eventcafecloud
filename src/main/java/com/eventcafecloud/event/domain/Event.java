package com.eventcafecloud.event.domain;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.type.EventCategory;
import com.eventcafecloud.event.dto.EventCreateRequestDto;
import com.eventcafecloud.event.dto.EventUpdateRequestDto;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_number")
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory eventCategory;

    @Column(nullable = false)
    private String eventStartDate;

    @Column(nullable = false)
    private String eventEndDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String eventInfo;

    @Column(nullable = false)
    private Integer eventPrice;

    @Column(nullable = false)
    private Boolean eventCancelAvail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_number")
    private Cafe cafe;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventImage> eventImages = new ArrayList<>();

    public Event(EventCreateRequestDto requestDto) {
        this.eventName = requestDto.getEventName();
        this.eventCategory = requestDto.getEventCategory();
        this.eventStartDate = requestDto.getEventStartDate();
        this.eventEndDate = requestDto.getEventEndDate();
        this.eventInfo = requestDto.getEventInfo();
        this.eventPrice = Integer.valueOf(requestDto.getEventPrice());
        this.eventCancelAvail = true;
    }

    public void updateEvent(EventUpdateRequestDto requestDto) {
        this.eventName = requestDto.getEventName();
        this.eventInfo = requestDto.getEventInfo();
    }

    public String getDate() {
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
        return today.format(new Date());
    }

    // 연관 관계 편의 메소드
    public void addUser(User user) {
        this.user = user;
        user.getEvents().add(this);
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getEvents().add(this);
    }

    public void addEventImage(EventImage eventImage) {
        eventImages.add(eventImage);
        eventImage.addEvent(this);
    }
}

