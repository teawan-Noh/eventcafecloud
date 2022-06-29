package com.eventcafecloud.user.domain;

import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.EventBookmark;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.type.ProviderType;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.domain.type.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class User extends BaseTimeEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number")
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String userEmail;

    @JsonIgnore
    @Column(length = 128, nullable = false)
    @Size(max = 128)
    private String userPassword;

    @Column(length = 100, nullable = false)
    private String userNickname;

    @Column(length = 100)
    private String userGender;

    @Lob
    @Column(length = 10000, nullable = false)
    private String userImage;

    @Column(length = 1, nullable = false)
    private String emailVerifiedYn;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ProviderType userRegPath;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private StatusType userStatus;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
    
    @OneToMany(mappedBy = "user")
    private List<EventBookmark> eventBookmarks = new ArrayList<>();

    @Builder
    public User(String userEmail, String userNickname, String userGender, String userImage,
                String emailVerifiedYn, ProviderType userRegPath, RoleType role, StatusType userStatus) {
        this.userEmail = userEmail;
        this.userPassword = "NO_PASS";
        this.userNickname = userNickname;
        this.userGender = userGender != null ? userGender : "NO_DATA";
        this.userImage = userImage != null ? userImage : "";
        this.emailVerifiedYn = emailVerifiedYn;
        this.userRegPath = userRegPath;
        this.role = role;
        this.userStatus = userStatus;
    }
    
}
