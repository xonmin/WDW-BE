package com.wdw.wdw.infra.oauth.provider;

import com.wdw.wdw.exception.InvalidProviderType;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

public interface UserInfoFactory {
    public OAuth2UserInfo makeUserInfo(OAuth2UserRequest userRequest, Map<String, Object> map) throws InvalidProviderType;
}
