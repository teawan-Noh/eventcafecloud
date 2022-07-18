package com.eventcafecloud.user.domain;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.user.domain.type.ApproveType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class HostUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_user_number")
    private Long id;

    private String userEmail;

    private String certificationFile;

    @Enumerated(EnumType.STRING)
    private ApproveType isApprove;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    public void addUser(User user) {
        this.user = user;
    }

    public void updateApprove(ApproveType approveType) {
        this.isApprove = approveType;
    }

    public void updateIsApprove() {
        this.isApprove = ApproveType.WAITING;
    }
}
