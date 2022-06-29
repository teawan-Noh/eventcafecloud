package com.eventcafecloud.event.domain;

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
public class EventImage extends Image {

    @Column(nullable = false)
    private String eventImageUrl;

    @Column(nullable = true)
    private LocalDateTime createdDate;    // BaseTImeEntity 상속 구현 (상속은 안되지만)

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    @Builder
    public EventImage(String eventImageUrl, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.eventImageUrl = eventImageUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
