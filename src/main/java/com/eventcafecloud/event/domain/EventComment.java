package com.eventcafecloud.event.domain;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.dto.EventCmtRequestDto;
import com.eventcafecloud.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_cmt_number")
    private Long id;

    @Column(nullable = false)
    private String eventCmtContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_number")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    public EventComment(EventCmtRequestDto requestDto) {
        this.eventCmtContent = requestDto.getEventCmtContent();
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addEvent(Event event) {
        this.event = event;
    }
}
