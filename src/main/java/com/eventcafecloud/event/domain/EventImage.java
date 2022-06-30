package com.eventcafecloud.event.domain;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.common.fileutil.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class EventImage {

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
