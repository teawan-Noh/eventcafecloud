package com.eventcafecloud.event.domain;


import com.eventcafecloud.common.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class EventImage extends BaseTimeEntity {

    @GeneratedValue
    @Column(name = "event_image_number")
    @Id
    private Long id;

    private String eventOriginImageName;

    private String eventImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    public EventImage(String eventOriginImageName, String eventImageUrl, Event event){
        this.eventOriginImageName = eventOriginImageName;
        this.eventImageUrl = eventImageUrl;
        this.event = event;
    }
}
