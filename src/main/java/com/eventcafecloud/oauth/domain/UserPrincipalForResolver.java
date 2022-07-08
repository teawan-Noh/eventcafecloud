package com.eventcafecloud.oauth.domain;


import com.eventcafecloud.user.domain.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipalForResolver {
    private String userEmail;
    private String userNickname;
    private RoleType authorities;
}
