package com.wdw.wdw.infra.oauth.provider;

import com.wdw.wdw.exception.InvalidProviderTypeException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserInfoFactoryImpl implements UserInfoFactory{
    @Override
    public OAuth2UserInfo makeUserInfo(OAuth2UserRequest userRequest, Map<String, Object> attributes) throws InvalidProviderTypeException {
        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google":
                return new GoogleUserInfo(attributes);
            default:
                throw new InvalidProviderTypeException();
        }
    }
}
