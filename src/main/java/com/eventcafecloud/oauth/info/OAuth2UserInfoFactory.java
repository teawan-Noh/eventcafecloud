package com.eventcafecloud.oauth.info;

import com.eventcafecloud.oauth.info.impl.GoogleOAuth2UserInfo;
import com.eventcafecloud.oauth.info.impl.KakaoOAuth2UserInfo;
import com.eventcafecloud.oauth.info.impl.NaverOAuth2UserInfo;
import com.eventcafecloud.user.domain.type.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes){
        switch (providerType){
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type");
        }
    }
}
