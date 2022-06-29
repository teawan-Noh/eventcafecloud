package com.eventcafecloud.oauth.service;

import com.eventcafecloud.oauth.domain.UserPrincipal;
import com.eventcafecloud.oauth.exception.OAuthProviderMissMatchException;
import com.eventcafecloud.oauth.info.OAuth2UserInfo;
import com.eventcafecloud.oauth.info.OAuth2UserInfoFactory;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.ProviderType;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.domain.type.StatusType;
import com.eventcafecloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    //인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이면 업데이트를 실행
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {

        //현재 진행중인 서비스를 구분하기 위해 문자열을 받음
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Optional<User> checkUser = userRepository.findByUserEmail(userInfo.getEmail());
        User savedUser = checkUser.isEmpty() ? createUser(userInfo, providerType) : checkUser.get();

        if (providerType != savedUser.getUserRegPath()) {
            throw new OAuthProviderMissMatchException(
                    "가입 경로가 잘못 되었습니다. " + savedUser.getUserRegPath() + "로 다시 로그인해주세요"
            );
        }
        updateUser(savedUser, userInfo);

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    //가져온 사용자 정보에 변경이 있다면 업데이트를 실행
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getNickname() != null && !user.getUserNickname().equals(userInfo.getNickname())) {
            user.setUserNickname(userInfo.getNickname());
        }

        if (userInfo.getUserImage() != null && !user.getUserImage().equals(userInfo.getUserImage())) {
            user.setUserImage(userInfo.getUserImage());
        }

        return user;
    }

    //가져온 사용자 정보를 통해서 회원가입 실행
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .userEmail(userInfo.getEmail())
                .userNickname(userInfo.getNickname())
//                .userGender(userInfo.getGender())
                .userImage(userInfo.getUserImage())
                .userRegPath(providerType)
                .userStatus(StatusType.ACTIVATE)
                .emailVerifiedYn("N")
                .role(RoleType.NORMAL)
                .build();

        return userRepository.saveAndFlush(user);
    }
}
