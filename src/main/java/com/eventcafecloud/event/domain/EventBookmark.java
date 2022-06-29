package com.eventcafecloud.event.domain;

import com.eventcafecloud.common.bookmark.Bookmark;
import com.eventcafecloud.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EventBookmark extends Bookmark {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    public EventBookmark(User user, Event event) {
        super.user = user;
        this.event = event;

        super.user.getEventBookmarks().add(this);
        this.event.getEventBookmarks().add(this);
    }
}
