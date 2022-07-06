package com.eventcafecloud.oauth.domain;

import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.ProviderType;
import com.eventcafecloud.user.domain.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
    private final String userEmail;
    private final String userPassword;
    private final ProviderType userRegPath;
    private final RoleType role;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //계정의 권한 목록을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //계정의 비밀번호를 리턴
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getName() {
        return userEmail;
    }

    //계정의 고유한 값을 리턴 (DB PK값, 중복이 없는 이메일 값 등)
    @Override
    public String getUsername() {
        return userEmail;
    }

    //계정의 만료 여부 리턴
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정의 잠김 여부 리턴
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정의 비밀번호 만료여부 리턴
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정의 활성화 여부 리턴
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUserEmail(),
                user.getUserPassword(),
                user.getUserRegPath(),
                user.getRole(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getCode()))
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }
}
