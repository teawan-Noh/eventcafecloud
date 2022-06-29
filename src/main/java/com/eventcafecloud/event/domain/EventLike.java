package com.eventcafecloud.event.domain;

import com.eventcafecloud.common.like.Like;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EventLike extends Like {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    public EventLike(User user, Event event) {
        super.user = user;
        this.event = event;

        super.user.getEventLikes().add(this);
        this.event.getEventLikes().add(this);
    }
}