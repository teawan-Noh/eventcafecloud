package com.eventcafecloud.oauth.info.impl;

import com.eventcafecloud.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");

        if (kakao_account == null) {
            return null;
        }

        return (String) kakao_account.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getGender() {
        return (String) attributes.get("gender");
    }

    @Override
    public String getUserImage() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }

}
