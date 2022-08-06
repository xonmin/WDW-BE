package com.wdw.wdw.config.oauth.provider;

import com.wdw.wdw.exception.InvalidProviderType;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class UserInfoFactoryImpl implements UserInfoFactory{
    @Override
    public OAuth2UserInfo makeUserInfo(OAuth2UserRequest userRequest, Map<String, Object> attributes) throws InvalidProviderType {
        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google":
                return new GoogleUserInfo(attributes);
            default:
                throw new InvalidProviderType("지원하지 않는 소셜 로그인 입니다.");
        }
    }
}
