package com.eventcafecloud.oauth.info.impl;

import com.eventcafecloud.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getGender() {
        return (String) attributes.get("gender");
    }

    @Override
    public String getUserImage() {
        return (String) attributes.get("picture");
    }
}
