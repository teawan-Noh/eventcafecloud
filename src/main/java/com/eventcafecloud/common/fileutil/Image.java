package com.eventcafecloud.common.fileutil;

import com.eventcafecloud.common.base.BaseTimeEntity;

import javax.persistence.*;

@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNumber;

    private String imageUrl;
}
