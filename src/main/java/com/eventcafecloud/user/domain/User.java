package com.eventcafecloud.user.domain;

import com.eventcafecloud.cafe.domain.Cafe;
import com.eventcafecloud.cafe.domain.CafeReview;
import com.eventcafecloud.comment.domain.Comment;
import com.eventcafecloud.common.base.BaseTimeEntity;
import com.eventcafecloud.event.domain.Event;
import com.eventcafecloud.post.domain.Post;
import com.eventcafecloud.user.domain.type.ApproveType;
import com.eventcafecloud.user.domain.type.ProviderType;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.domain.type.StatusType;
import com.eventcafecloud.user.dto.UserRequestDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number")
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String userEmail;

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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ProviderType userRegPath;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private StatusType userStatus;

    @OneToMany(mappedBy = "user")
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cafe> cafes = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private HostUser hostUser;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CafeReview> cafeReviews = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<EventBookmark> eventBookmarks = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<EventLike> eventLikes = new ArrayList<>();

    @Builder
    public User(String userEmail, String userNickname, String userGender, String userImage,
                ProviderType userRegPath, RoleType role, StatusType userStatus) {
        this.userEmail = userEmail;
        this.userPassword = "NO_PASS";
        this.userNickname = userNickname;
        this.userGender = userGender != null ? userGender : "NO_DATA";
        this.userImage = userImage != null ? userImage : "";
        this.userRegPath = userRegPath;
        this.role = role;
        this.userStatus = userStatus;
    }

    public void registHost(HostUser hostUser) {
        this.hostUser = hostUser;
        hostUser.addUser(this);
    }

    public void updateNormalUserToHostUser(User user) {
        user.updateRole(RoleType.HOST);
        user.getHostUser().updateApprove(ApproveType.PASS);
    }

    public void updateUserRoleAndUserStatus(UserRequestDto requestDto) {
        RoleType roleType = RoleType.of(requestDto.getRole());
        StatusType statusType = StatusType.of(requestDto.getUserStatus());
        this.role = roleType;
        this.userStatus = statusType;
    }

    public void updateRole(RoleType role) {
        this.role = role;
    }

    public void updateProfile(UserRequestDto requestDto, String userImage) {
        this.userImage = userImage;
        this.userNickname = requestDto.getUserNickname();
    }

    public void addCafe(Cafe cafe) {
        this.cafes.add(cafe);
        cafe.addUser(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.addUser(this);
    }

    public void addCafeReview(CafeReview cafeReview) {
        cafeReview.addUser(this);
        this.cafeReviews.add(cafeReview);
    }

}
