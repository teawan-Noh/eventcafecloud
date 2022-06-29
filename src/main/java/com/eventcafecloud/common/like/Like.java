package com.eventcafecloud.common.like;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.user.domain.User;

import javax.persistence.*;

//@DiscriminatorColumn
//@Inheritance(strategy = InheritanceType.JOINED)
//@Entity
//public abstract class Like extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long likeNumber;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    protected User user;
//}