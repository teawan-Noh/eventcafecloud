package com.eventcafecloud.event.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class EventImage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_image_number")
    @Id
    private Long id;

    private String eventOriginImageName;

    private String eventImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    public EventImage(String eventOriginImageName, String eventImageUrl) {
        this.eventOriginImageName = eventOriginImageName;
        this.eventImageUrl = eventImageUrl;
    }

    public void addEvent(Event event){
        this.event = event;
    }
}
