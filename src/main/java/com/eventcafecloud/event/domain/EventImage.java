package com.eventcafecloud.event.domain;

import com.eventcafecloud.common.fileutil.Image;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EventImage extends Image {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;
}
