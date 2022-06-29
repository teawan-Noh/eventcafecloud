package com.eventcafecloud.common.bookmark;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.user.domain.User;

import javax.persistence.*;

@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Bookmark extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookmarkNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    protected User user;
}
